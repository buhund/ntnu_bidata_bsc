## Introduction

Persistence is made possible through the use of so-called Reader and Writer classes and methods.

The application features persistent storage of files containing budget and accounting entries. These are stored locally, as CSV-files (comma separated value). This ensures that the application is actually useable, in that the data is stores and will be available the next time the program is opened. Additionally they can be edited and deleted.

Since the information is stored in a format that is widely supported by other applications, especially spreadsheets, the data can be extracted from the application and imported into another program or viewed/edited directly in a text editor. Portability of data is an important principle, and even though this specific finctionality wasn't implemented via the GUI, it will still be possible to retrieve the data from the JAR file by browsing it as an archive.

The applications uses 10 different classes to handle the reading and writing:

```
Baecon/src/main/java/edu/ntnu/idatt1002/baecon/readersAndWriters/
├── AccountingReader.java
├── AccountingWriter.java
├── BudgetReader.java
├── BudgetWriter.java
├── CategoryReader.java
├── CategoryWriter.java
├── DocumentReader.java
├── DocumentWriter.java
├── SettingsReader.java
└── SettingsWriter.java
```

## Code

Readers and Writers are responsible for handling the writing of new data, and the reading of stored data. Below are two code examples; one reader and one writer. 

#### Accounting Writer

This is the reader responsible for reading all entries for a given month from the correct file. The files are saved as CSV-files that follows the naming scheme "mm.yyyy", e.g. "04.2023", which contains the accounting entries for April 2023. Each entry is also tied to different UUIDs; one which connects it to a specific category, one that connects to a document, if that was attached upon creating the entry, and one that acts as the entry's unique ID.

<details>
    <summary>Click to expand or collapse the code block</summary>

```java
package edu.ntnu.idatt1002.baecon.readersAndWriters;

import edu.ntnu.idatt1002.baecon.data.AccountingEntry;
import edu.ntnu.idatt1002.baecon.utilities.BaeconDateFormatter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

/**
 * Class responsible for reading {@link AccountingEntry} files.
 */
public class AccountingReader {
  private static final String delimiter = ",|(\r\n|\n)";

  /**
   * Method to read all {@link AccountingEntry} objects from a file.
   *
   * @param file The file to read from.
   * @return A list of {@link AccountingEntry} objects.
   * @throws IOException If the file is not found or the file format is not supported.
   * @throws ParseException If the date format is not supported.
   */
  public List<AccountingEntry> readAllAccountingEntriesInAMonthFromFile(File file)
      throws IOException, ParseException {
    if (!file.getName().endsWith(".csv")) {
      throw new IOException("Unsupported file format. Only .csv files are supported.");
    }
    List<AccountingEntry> accountingEntries = new ArrayList<>();
    try (Scanner accountingEntryFileReader = new Scanner(file, StandardCharsets.UTF_8)) {
      accountingEntryFileReader.useDelimiter(delimiter);
      while (accountingEntryFileReader.hasNext()) {

        String idAsString = accountingEntryFileReader.next();
        UUID id = UUID.fromString(idAsString);

        boolean expense = Boolean.parseBoolean(accountingEntryFileReader.next());

        LocalDate formatDate = BaeconDateFormatter.parse(accountingEntryFileReader.next());

        double amount = Double.parseDouble(accountingEntryFileReader.next());

        String categoryIdAsString = accountingEntryFileReader.next();
        UUID categoryId = UUID.fromString(categoryIdAsString);

        String description = accountingEntryFileReader.next();

        String pdfIdAsString = accountingEntryFileReader.next();
        UUID pdfId;
        if (pdfIdAsString.equals("null")) {
          pdfId = null;
        } else {
          pdfId = UUID.fromString(pdfIdAsString);
        }

        AccountingEntry accountingEntry = new AccountingEntry(id, expense,
            formatDate, amount, categoryId, description, pdfId);
        accountingEntries.add(accountingEntry);
      }
    }
    return accountingEntries;
  }
}
```
</details>

#### Category Reader

The Reader for categories. Each category must be unique in name. They are assigned a unique UUID to differentiate and identify them. This UUID is also used to enable category assignment to budget and accounting entries. Cateories are read and written from a single .csv-file.

<details>
    <summary>Click to expand or collapse the code block</summary>

```java
package edu.ntnu.idatt1002.baecon.readersAndWriters;

import edu.ntnu.idatt1002.baecon.data.Category;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

/**
 * Class responsible for reading {@link Category} files.
 */
public class CategoryReader {
  private final String delimiter = ",|(\r\n|\n)"; // + System.lineSeparator();

  /**
   * Reads all categories from a file.
   *
   * @return A {@link HashMap} of {@link Category} objects.
   * @throws IOException if an I/O error occurs.
   */
  public HashMap<UUID, Category> readAllCategoriesFromFile(File file) throws IOException {
    if (!file.getName().endsWith(".csv")) {
      throw new IOException("Unsupported file format. Only .csv files are supported.");
    }
    HashMap<UUID, Category> categories = new HashMap<>();
    try (Scanner fileReader = new Scanner(file, StandardCharsets.UTF_8)) {
      fileReader.useDelimiter(delimiter);
      while (fileReader.hasNext()) {
        String idAsString = fileReader.next();
        UUID id = UUID.fromString(idAsString);
        String description = fileReader.next();
        Category category = new Category(id, description);
        categories.put(id, category);
      }
    }
    return categories;
  }
}

```
</details>

## Readers and Writers class diagrams

Below are the class diagrams for all reader and writer classes.

<details>
    <summary>Click to expand or collapse the image block</summary>

![classDiagram-persistence-readersAndWriters](uploads/34381e4c703a082de5be92491cdaf7a9/classDiagram-persistence-readersAndWriters.png)

</details>

## Persistent Files

Below are examples of the persistent files, one for accounting and one for budget, for the same period (April 2023). The files are currently populated with dummy data.

Accounting entries for 2023.04

```
a1f1b864-b339-476f-8225-939b8bc89cba,false,15.04.2023 00:00:00,9600.0,fffe76ce-f383-486b-9390-74d94f1bd6b9,Stipend og studielån,null
4966bcbc-be25-49d8-856e-c27e04445f15,true,01.04.2023 00:00:00,6200.0,2bae9d28-054f-4c38-be45-764c0bf78e4b,Husleie,null
36b019a6-f373-4d24-b0a4-ce54648c9129,true,18.04.2023 00:00:00,319.0,98d858de-4f93-4d12-8d6a-28931bd20221,Nettleie Tensio,null
2df48d6f-13b9-4191-9e33-b2d20f67debf,true,18.04.2023 00:00:00,359.0,395c0c66-3d54-4c53-997d-d5ee13ec71fa,Strøm Motkraft,null

```

Budget entries for 2023.04

```
c537334a-f726-4f6f-b683-6a4c47c7ad38,false,9600.0,fffe76ce-f383-486b-9390-74d94f1bd6b9
53ef7f60-0258-4ccc-a432-5a1124b36e10,true,6200.0,2bae9d28-054f-4c38-be45-764c0bf78e4b
d34c8435-736a-475f-b076-0e7165d52307,true,320.0,98d858de-4f93-4d12-8d6a-28931bd20221
c4f4e8aa-3a76-4140-95f4-91351b621a95,true,500.0,395c0c66-3d54-4c53-997d-d5ee13ec71fa
bc7178c3-e327-4538-90f6-51f0e85a12fb,true,230.0,f58f6856-bbe5-495a-a723-3b6a88e54174
9cf435c1-9b52-414e-ab1e-261d0c063054,true,129.0,c7f142bd-7b09-4b2f-ae96-2c344191fd02
e318589b-8985-4d79-a5e0-caed53bcb34d,true,199.0,c7f142bd-7b09-4b2f-ae96-2c344191fd02

```
