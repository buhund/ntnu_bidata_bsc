/**
 * @project Paths-App
 * @author Gruppe 12, IDATT2001
 */

package edu.ntnu.idatt2001.paths.builders;

import javafx.geometry.HPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;

/**
 * Builder/Helper class for building GridPane Column Constraints.
 */
public class ColumnConstraintsBuilder {
  private double minWidth;
  private double prefWidth;
  private double maxWidth;

  /**
   * Builder method constructor
   */
  private ColumnConstraintsBuilder() {}

  /**
   * Creator method.
   *
   * @return new ColumnConstraintsBuilder()
   */
  public static ColumnConstraintsBuilder create() {
    return new ColumnConstraintsBuilder();
  }

  /**
   * Minimum column width builder.
   *
   * @param minWidth
   * @return this
   */
  public ColumnConstraintsBuilder withMinWidth(double minWidth) {
    this.minWidth = minWidth;
    return this;
  }

  /**
   * Preferred column width builder.
   * @param prefWidth
   * @return this
   */
  public ColumnConstraintsBuilder withPrefWidth(double prefWidth) {
    this.prefWidth = prefWidth;
    return this;
  }

  /**
   * Maximum column width builder.
   * @param maxWidth
   * @return this
   */
  public ColumnConstraintsBuilder withMaxWidth(double maxWidth) {
    this.maxWidth = maxWidth;
    return this;
  }

  /**
   * Build method.
   * @return columnConstraints
   */
  public ColumnConstraints build() {
    ColumnConstraints columnConstraints = new ColumnConstraints();
    columnConstraints.setHgrow(Priority.ALWAYS);
    columnConstraints.setHalignment(HPos.CENTER);
    columnConstraints.setMinWidth(minWidth);
    columnConstraints.setPrefWidth(prefWidth);
    columnConstraints.setMaxWidth(maxWidth);
    return columnConstraints;
  }

}
