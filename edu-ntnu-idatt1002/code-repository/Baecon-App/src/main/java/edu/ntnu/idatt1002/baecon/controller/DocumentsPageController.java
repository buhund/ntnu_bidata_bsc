package edu.ntnu.idatt1002.baecon.controller;

import edu.ntnu.idatt1002.baecon.components.ErrorAlert;
import edu.ntnu.idatt1002.baecon.data.Document;
import edu.ntnu.idatt1002.baecon.factory.DocumentsControllerFactory;
import edu.ntnu.idatt1002.baecon.listeners.DocumentsChangeListener;
import edu.ntnu.idatt1002.baecon.utilities.BaeconDateFormatter;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

/**
 * Class responsible for handling the documents page.
 */
public class DocumentsPageController implements DocumentsChangeListener {
  private DocumentsController documentsController;
  @FXML private TableView<Document> documentsTableView;
  @FXML private TableColumn<Document, String> documentNameColumn;
  @FXML private TableColumn<Document, LocalDate> documentDateColumn;
  @FXML private Text documentFileNameText;
  @FXML private TextField searchTextField;
  @FXML private Button openDocumentButton;
  @FXML private Button deleteDocumentButton;
  @FXML private ScrollPane documentScrollPane;

  /**
   * Method to initialize the controller.
   */
  @FXML
  public void initialize() {
    documentsController = DocumentsControllerFactory.getDocumentsController();
    documentsController.addSubscriber(this);

    WebView webView = new WebView();
    ImageView imageView = new ImageView();

    documentsTableView.getSelectionModel().selectedItemProperty()
        .addListener((obs, oldSelection, newSelection) -> {
          if (newSelection != null) {
            documentScrollPane.setContent(null);
            if (oldSelection != null) {
              webView.getEngine().loadContent("");
            }
            deleteDocumentButton.setDisable(false);
            if (!newSelection.getPdfFile().exists()) {
              documentFileNameText.setText("Document File Name: "
                  + newSelection.getPdfFile().getName() + " (File not found)");
              return;
            }
            documentFileNameText.setText("Document File Name: "
                + newSelection.getPdfFile().getName());
            openDocumentButton.setDisable(false);

            if (newSelection.getPdfFile().getName().endsWith(".pdf")) {
              try {
                documentScrollPane.setContent(imageView);
                PDDocument pdDocument = PDDocument.load(newSelection.getPdfFile());
                PDFRenderer renderer = new PDFRenderer(pdDocument);
                BufferedImage image = renderer.renderImage(0);
                Image fxImage = SwingFXUtils.toFXImage(image, null);
                imageView.setImage(fxImage);
                pdDocument.close();
              } catch (IOException e) {
                e.printStackTrace();
                ErrorAlert.show("Error opening document: ", e.getMessage());
              }
              return;
            }

            documentScrollPane.setContent(webView);
            webView.getEngine().load(newSelection.getPdfFile().toURI().toString());
          } else {
            openDocumentButton.setDisable(true);
            deleteDocumentButton.setDisable(true);
            documentFileNameText.setText("Document File Name: ");
          }
        });
    // Set the call value factory to the same as the variable on the object
    documentNameColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    documentDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

    // Custom logic to format the date
    documentDateColumn.setCellFactory(column -> new TableCell<>() {
      @Override
      protected void updateItem(LocalDate item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
          setText(null);
        } else {
          setText(BaeconDateFormatter.formatWithoutTime(item));
        }
      }
    });

    searchTextField.textProperty().addListener((observable, oldValue, newValue) ->
        documentsController.searchDocumentsByDescription(newValue));

    openDocumentButton.setOnAction(actionEvent -> {
      Document document = documentsTableView.getSelectionModel().getSelectedItem();
      if (document != null) {
        try {
          String os = System.getProperty("os.name").toLowerCase();
          if (os.contains("linux")) {
            ProcessBuilder pb = new ProcessBuilder("xdg-open",
                document.getPdfFile().getAbsolutePath());
            pb.start();
            return;
          }
          Desktop.getDesktop().open(document.getPdfFile());
        } catch (Exception e) {
          ErrorAlert.show("Error opening document: ", e.getMessage());
        }
      }
    });


    showDocuments(documentsController.getDocuments());
  }

  /**
   * Method to show a dialog to confirm deletion of a document.
   */
  @FXML
  public void onDeleteDocument() {
    Document document = documentsTableView.getSelectionModel().getSelectedItem();
    Dialog<ButtonType> deleteDocumentDialog = new Dialog<>();
    deleteDocumentDialog.setTitle("Baecon - Delete entry");
    deleteDocumentDialog.setHeaderText("Are you sure you want to delete document "
        + document.getDescription() + "?");

    ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
    ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
    deleteDocumentDialog.getDialogPane().getButtonTypes().addAll(yesButton, noButton);

    deleteDocumentDialog.setResultConverter(buttonType -> {
      if (buttonType == yesButton) {
        return buttonType;
      }
      return null;
    });

    Optional<ButtonType> result = deleteDocumentDialog.showAndWait();
    if (result.isPresent() && result.get() == yesButton) {
      try {
        documentsController.deleteDocument(document);
      } catch (IOException ioException) {
        ioException.printStackTrace();
        ErrorAlert.show("Error deleting document: ", "Error: "
            + ioException.getMessage());
      }
    }
  }

  /**
   * Method to show the documents in the table view.
   *
   * @param documents The documents to show.
   */
  private void showDocuments(List<Document> documents) {
    documentsTableView.getItems().clear();
    for (Document document : documents) {
      documentsTableView.getItems().add(document);
    }
  }

  @Override
  public void updateDocuments(List<Document> documents) {
    showDocuments(documents);
  }
}
