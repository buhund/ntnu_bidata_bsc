## Introduction

The project was set up to adhere to standard package structuring, as outlined by the [Maven documentation](https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html), in addition to keeping the non-Maven-specific structure and naming as coherent, descriptive and consise as possible.

## Package structure

The following tree structure shows the main packages. Some directories are not shown with their subdirectories and files, since their contents are not crucial to show the overarching structure. Some directories and files have been removed entirely from this view. The full project structure can be found further down on this page (click [here](https://gitlab.stud.idi.ntnu.no/team_01-idatt1002/project-assignment-idatt1002-y2023_spring-t01/-/wikis/Project-Structure#full-project-structure) to go there now).


#### Main structure
<details>
    <summary>Click to expand or collapse the code block</summary>

```
Baecon/src/main/
├── java
│   └── edu
│       └── ntnu
│           └── idatt1002
│               └── baecon
│                   ├── BaeconApplication.java
│                   ├── components
│                   │   ├── ErrorAlert.java
│                   │   └── NumberAndPunctuationTextField.java
│                   ├── controller
│                   │   ├── AboutDialogController.java
│                   │   ├── AccountingEntriesController.java
│                   │   ├── AccountingPageController.java
│                   │   ├── AddAccountingEntryController.java
│                   │   ├── AddBudgetEntryController.java
│                   │   ├── BudgetEntriesController.java
│                   │   ├── BudgetPageController.java
│                   │   ├── CategoriesController.java
│                   │   ├── DashboardPageController.java
│                   │   ├── DocumentsController.java
│                   │   ├── DocumentsPageController.java
│                   │   ├── EditAccountingEntryController.java
│                   │   ├── GeneralLayoutController.java
│                   │   ├── SettingsCategoryDialogController.java
│                   │   ├── SettingsController.java
│                   │   └── SettingsPageController.java
│                   ├── data
│                   │   ├── AccountingEntry.java
│                   │   ├── BudgetEntry.java
│                   │   ├── Category.java
│                   │   ├── Document.java
│                   │   ├── Entry.java
│                   │   └── Settings.java
│                   ├── factory
│                   │   ├── AccountingEntriesControllerFactory.java
│                   │   ├── BudgetEntriesControllerFactory.java
│                   │   ├── CategoriesControllerFactory.java
│                   │   ├── DocumentsControllerFactory.java
│                   │   └── SettingsControllerFactory.java
│                   ├── listeners
│                   │   ├── AccountingEntriesChangeListener.java
│                   │   ├── BudgetEntriesChangeListener.java
│                   │   ├── CategoriesChangeListener.java
│                   │   ├── DocumentsChangeListener.java
│                   │   └── SettingsListener.java
│                   ├── MainApp.java
│                   ├── readersAndWriters
│                   │   ├── AccountingReader.java
│                   │   ├── AccountingWriter.java
│                   │   ├── BudgetReader.java
│                   │   ├── BudgetWriter.java
│                   │   ├── CategoryReader.java
│                   │   ├── CategoryWriter.java
│                   │   ├── DocumentReader.java
│                   │   ├── DocumentWriter.java
│                   │   ├── SettingsReader.java
│                   │   └── SettingsWriter.java
│                   ├── scenes
│                   │   ├── View.java
│                   │   └── ViewSwitcher.java
│                   └── utilities
│                       ├── BaeconDateFormatter.java
│                       └── BaeconFiles.java
└── resources
    └── edu
        └── ntnu
            └── idatt1002
                └── baecon
                    ├── dataFiles
                    │   ├── accountingEntries
                    │   │   ├── 2023.01.csv
                    │   │   ├── 2023.02.csv
                    │   │   ├── ........csv
                    │   │   ├── 2023.12.csv
                    │   ├── budgetEntries
                    │   │   ├── 2023.01.csv
                    │   │   ├── 2023.02.csv
                    │   │   ├── ........csv
                    │   │   ├── 2023.12.csv
                    │   ├── categories.csv
                    │   ├── documents.csv
                    │   └── settings.ser
                    ├── documents
                    │   └── 100-ola-nordmann-gmail-com.pdf
                    └── scenes
                        ├── aboutDialog.fxml
                        ├── accountingPage.fxml
                        ├── addAccountingEntry.fxml
                        ├── addBudgetEntry.fxml
                        ├── budgetPage.fxml
                        ├── css
                        │   ├── barChart.css
                        │   └── dark-mode.css
                        ├── dashboardPage.fxml
                        ├── documentsPage.fxml
                        ├── editAccountingEntry.fxml
                        ├── generalLayout.fxml
                        ├── img
                        │   ├── logo-banner-transparent-01.png
                        │   └── logo-transparent-no_title-02-100px.png
                        ├── settingsCategoryDialog.fxml
                        └── settingsPage.fxml

```
</details>

#### Test structure

<details>
    <summary>Click to expand or collapse the code block</summary>

```
Baecon/src/test/
├── java
│   └── edu
│       └── ntnu
│           └── idatt1002
│               └── baecon
│                   ├── controller
│                   │   ├── AccountingEntriesControllerTest.java
│                   │   ├── BudgetEntriesControllerTest.java
│                   │   ├── CategoriesControllerTest.java
│                   │   ├── DocumentsControllerTest.java
│                   │   └── SettingsControllerTest.java
│                   ├── data
│                   │   ├── AccountingEntryTest.java
│                   │   ├── BudgetEntryTest.java
│                   │   ├── CategoryTest.java
│                   │   ├── DocumentTest.java
│                   │   └── SettingsTest.java
│                   ├── readersAndWriters
│                   │   ├── AccountingReaderTest.java
│                   │   ├── AccountingWriterTest.java
│                   │   ├── BudgetReaderTest.java
│                   │   ├── BudgetWriterTest.java
│                   │   ├── CategoryReaderTest.java
│                   │   ├── CategoryWriterTest.java
│                   │   ├── DocumentReaderTest.java
│                   │   ├── DocumentWriterTest.java
│                   │   ├── SettingsReaderTest.java
│                   │   └── SettingsWriterTest.java
│                   └── utilities
│                       ├── BaeconDateFormatterTest.java
│                       └── BaeconFilesTest.java
└── resources
    └── edu
        └── ntnu
            └── idatt1002
                └── baecon
                    └── dataFiles
                        ├── accountingEntries
                        │   └── 2023.04.csv
                        ├── budgetEntries
                        │   └── 2023.04.csv
                        ├── categories.csv
                        ├── documents.csv
                        └── settings.ser
```

</details>


## Dependencies

Dependencies, as per our POM.xml file:
- JavaFX Controls, version 17.0.1
- JavaFX FXML, version 17.0.1
- JavaFX Graphics, version 17.0.1
- JavaFX Web, version 11.0.2
- JavaFX Media, version 11.0.2
- JUnit Jupiter API, version 5.8.1
- JUnit Jupiter Engine, version 5.8.1

**POM.xml > dependencies:**

<details>
    <summary>Click to expand or collapse the code block</summary>

```xml
 <dependencies>
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-controls</artifactId>
            <version>${javafx.version}</version>
        </dependency>

        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-fxml</artifactId>
            <version>${javafx.version}</version>
        </dependency>

        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-graphics</artifactId>
            <version>${javafx.version}</version>
        </dependency>

        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-web</artifactId>
            <version>11.0.2</version>
        </dependency>

        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-media</artifactId>
            <version>11.0.2</version>
        </dependency>

        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>${junit.version}</version>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-engine</artifactId>
            <version>${junit.version}</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
```

</details>


## Plugins

Plugins, as per our POM.xml file:
- Maven Compiler, version 3.8.1
- Maven JAR, version 2.4
- Maven Dependency, version 3.3.0
- Maven Resources, version 3.3.0
- Maven Surefire, version 3.0.0-M5
- JavaFX Maven, version 0.0.8

**POM.xml > build > pluigns:**

<details>
    <summary>Click to expand or collapse the code block</summary>

```xml
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>17</source>
                    <target>17</target>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <version>2.4</version>
                <configuration>
                    <archive>
                        <manifest>
                            <addClasspath>true</addClasspath>
                            <classpathPrefix>lib/</classpathPrefix>
                            <mainClass>edu.ntnu.idatt1002.baecon.MainApp</mainClass>
                        </manifest>
                        <manifestEntries>
                            <Class-Path>.</Class-Path>
                        </manifestEntries>
                    </archive>

                    <finalName>Baecon/Baecon</finalName>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-dependency-plugin</artifactId>
                <version>3.3.0</version>
                <executions>
                    <execution>
                        <id>copy-dependencies</id>
                        <phase>prepare-package</phase>
                        <goals>
                            <goal>copy-dependencies</goal>
                        </goals>
                        <configuration>
                            <outputDirectory>${project.build.directory}/Baecon/lib</outputDirectory>
                            <overWriteReleases>false</overWriteReleases>
                            <overWriteSnapshots>false</overWriteSnapshots>
                            <overWriteIfNewer>true</overWriteIfNewer>
                        </configuration>
                    </execution>
                </executions>
            </plugin>

            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-resources-plugin</artifactId>
                <version>3.3.0</version>
                <executions>
                    <execution>
                        <id>copy-resources</id>
                        <phase>validate</phase>
                        <goals>
                            <goal>copy-resources</goal>
                        </goals>
                        <configuration>
                            <outputDirectory>${basedir}/target/Baecon</outputDirectory>
                            <resources>
                                <resource>
                                    <directory>resources</directory>
                                    <filtering>true</filtering>
                                </resource>
                            </resources>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.0.0-M5</version>
            </plugin>
            <plugin>
                <groupId>org.openjfx</groupId>
                <artifactId>javafx-maven-plugin</artifactId>
                <version>0.0.8</version>
                <executions>
                    <execution>
                        <id>default-cli</id>
                        <configuration>
                            <mainClass>edu.ntnu.idatt1002.baecon.MainApp</mainClass>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
```

</details>


## Full Project Structure

`tree` printout of the full project structure (final delivery package).

<details>
    <summary>Click to expand or collapse the code block</summary>

```
Baecon/
├── idatt1002baecon.iml
├── javadoc
│   ├── allclasses-index.html
│   ├── allpackages-index.html
│   ├── copy.svg
│   ├── edu
│   │   └── ntnu
│   │       └── idatt1002
│   │           └── baecon
│   │               ├── BaeconApplication.html
│   │               ├── components
│   │               │   ├── ErrorAlert.html
│   │               │   ├── NumberAndPunctuationTextField.html
│   │               │   ├── package-summary.html
│   │               │   └── package-tree.html
│   │               ├── controller
│   │               │   ├── AboutDialogController.html
│   │               │   ├── AccountingEntriesController.html
│   │               │   ├── AccountingPageController.html
│   │               │   ├── AddAccountingEntryController.html
│   │               │   ├── AddBudgetEntryController.html
│   │               │   ├── BudgetEntriesController.html
│   │               │   ├── BudgetPageController.html
│   │               │   ├── CategoriesController.html
│   │               │   ├── CategoriesControllerTest.html
│   │               │   ├── DashboardPageController.html
│   │               │   ├── DashboardPageController.TableViewRow.html
│   │               │   ├── DocumentsController.html
│   │               │   ├── DocumentsPageController.html
│   │               │   ├── EditAccountingEntryController.html
│   │               │   ├── GeneralLayoutController.html
│   │               │   ├── package-summary.html
│   │               │   ├── package-tree.html
│   │               │   ├── SettingsCategoryDialogController.html
│   │               │   ├── SettingsController.html
│   │               │   └── SettingsPageController.html
│   │               ├── data
│   │               │   ├── AccountingEntry.html
│   │               │   ├── BudgetEntry.html
│   │               │   ├── Category.html
│   │               │   ├── Document.html
│   │               │   ├── Entry.html
│   │               │   ├── package-summary.html
│   │               │   ├── package-tree.html
│   │               │   ├── Receipt.html
│   │               │   ├── Settings.html
│   │               │   └── SettingsTest.html
│   │               ├── factory
│   │               │   ├── AccountingEntriesControllerFactory.html
│   │               │   ├── BudgetEntriesControllerFactory.html
│   │               │   ├── CategoriesControllerFactory.html
│   │               │   ├── DocumentsControllerFactory.html
│   │               │   ├── package-summary.html
│   │               │   ├── package-tree.html
│   │               │   └── SettingsControllerFactory.html
│   │               ├── gui
│   │               │   ├── package-summary.html
│   │               │   ├── package-tree.html
│   │               │   └── Viewport.html
│   │               ├── listeners
│   │               │   ├── AccountingEntriesChangeListener.html
│   │               │   ├── BudgetEntriesChangeListener.html
│   │               │   ├── CategoriesChangeListener.html
│   │               │   ├── DocumentsChangeListener.html
│   │               │   ├── package-summary.html
│   │               │   ├── package-tree.html
│   │               │   └── SettingsListener.html
│   │               ├── MainApp.html
│   │               ├── package-summary.html
│   │               ├── package-tree.html
│   │               ├── readersAndWriters
│   │               │   ├── AccountingReader.html
│   │               │   ├── AccountingWriter.html
│   │               │   ├── BudgetReader.html
│   │               │   ├── BudgetReaderTest.html
│   │               │   ├── BudgetWriter.html
│   │               │   ├── BudgetWriterTest.html
│   │               │   ├── CategoryReader.html
│   │               │   ├── CategoryReaderTest.html
│   │               │   ├── CategoryWriter.html
│   │               │   ├── CategoryWriterTest.html
│   │               │   ├── DocumentReader.html
│   │               │   ├── DocumentWriter.html
│   │               │   ├── package-summary.html
│   │               │   ├── package-tree.html
│   │               │   ├── SettingsReader.html
│   │               │   ├── SettingsReaderTest.html
│   │               │   ├── SettingsWriter.html
│   │               │   └── SettingsWriterTest.html
│   │               ├── scenes
│   │               │   ├── package-summary.html
│   │               │   ├── package-tree.html
│   │               │   ├── View.html
│   │               │   └── ViewSwitcher.html
│   │               └── utilities
│   │                   ├── BaeconDateFormatter.html
│   │                   ├── BaeconDateFormatterTest.html
│   │                   ├── BaeconFiles.html
│   │                   ├── BaeconFilesTest.html
│   │                   ├── package-summary.html
│   │                   └── package-tree.html
│   ├── element-list
│   ├── help-doc.html
│   ├── index-files
│   │   ├── index-10.html
│   │   ├── index-11.html
│   │   ├── index-12.html
│   │   ├── index-13.html
│   │   ├── index-14.html
│   │   ├── index-15.html
│   │   ├── index-16.html
│   │   ├── index-17.html
│   │   ├── index-18.html
│   │   ├── index-19.html
│   │   ├── index-1.html
│   │   ├── index-20.html
│   │   ├── index-2.html
│   │   ├── index-3.html
│   │   ├── index-4.html
│   │   ├── index-5.html
│   │   ├── index-6.html
│   │   ├── index-7.html
│   │   ├── index-8.html
│   │   └── index-9.html
│   ├── index.html
│   ├── jquery-ui.overrides.css
│   ├── legal
│   │   ├── ADDITIONAL_LICENSE_INFO
│   │   ├── ASSEMBLY_EXCEPTION
│   │   ├── COPYRIGHT
│   │   ├── jquery.md
│   │   ├── jqueryUI.md
│   │   └── LICENSE
│   ├── member-search-index.js
│   ├── module-search-index.js
│   ├── overview-summary.html
│   ├── overview-tree.html
│   ├── package-search-index.js
│   ├── resources
│   │   ├── glass.png
│   │   └── x.png
│   ├── script-dir
│   │   ├── jquery-3.6.0.min.js
│   │   ├── jquery-ui.min.css
│   │   └── jquery-ui.min.js
│   ├── script.js
│   ├── search.html
│   ├── search.js
│   ├── search-page.js
│   ├── serialized-form.html
│   ├── stylesheet.css
│   ├── tag-search-index.js
│   └── type-search-index.js
├── lib
│   ├── hamcrest-core-1.3.jar
│   └── junit-4.12.jar
├── LICENSE.md
├── logo
│   ├── iconBaecon.png
│   ├── logo-banner-transparent-01.png
│   ├── logo-greenSquare-no_title-03.png
│   ├── logo-transparent-no_title-01.png
│   └── logo-transparent-title-01.png
├── out
│   └── artifacts
│       └── Baecon_jar
│           └── Baecon.jar
├── pom.xml
├── README.md
├── src
│   ├── main
│   │   ├── java
│   │   │   └── edu
│   │   │       └── ntnu
│   │   │           └── idatt1002
│   │   │               └── baecon
│   │   │                   ├── BaeconApplication.java
│   │   │                   ├── components
│   │   │                   │   ├── ErrorAlert.java
│   │   │                   │   └── NumberAndPunctuationTextField.java
│   │   │                   ├── controller
│   │   │                   │   ├── AboutDialogController.java
│   │   │                   │   ├── AccountingEntriesController.java
│   │   │                   │   ├── AccountingPageController.java
│   │   │                   │   ├── AddAccountingEntryController.java
│   │   │                   │   ├── AddBudgetEntryController.java
│   │   │                   │   ├── BudgetEntriesController.java
│   │   │                   │   ├── BudgetPageController.java
│   │   │                   │   ├── CategoriesController.java
│   │   │                   │   ├── DashboardPageController.java
│   │   │                   │   ├── DocumentsController.java
│   │   │                   │   ├── DocumentsPageController.java
│   │   │                   │   ├── EditAccountingEntryController.java
│   │   │                   │   ├── GeneralLayoutController.java
│   │   │                   │   ├── SettingsCategoryDialogController.java
│   │   │                   │   ├── SettingsController.java
│   │   │                   │   └── SettingsPageController.java
│   │   │                   ├── data
│   │   │                   │   ├── AccountingEntry.java
│   │   │                   │   ├── BudgetEntry.java
│   │   │                   │   ├── Category.java
│   │   │                   │   ├── Document.java
│   │   │                   │   ├── Entry.java
│   │   │                   │   └── Settings.java
│   │   │                   ├── factory
│   │   │                   │   ├── AccountingEntriesControllerFactory.java
│   │   │                   │   ├── BudgetEntriesControllerFactory.java
│   │   │                   │   ├── CategoriesControllerFactory.java
│   │   │                   │   ├── DocumentsControllerFactory.java
│   │   │                   │   └── SettingsControllerFactory.java
│   │   │                   ├── listeners
│   │   │                   │   ├── AccountingEntriesChangeListener.java
│   │   │                   │   ├── BudgetEntriesChangeListener.java
│   │   │                   │   ├── CategoriesChangeListener.java
│   │   │                   │   ├── DocumentsChangeListener.java
│   │   │                   │   └── SettingsListener.java
│   │   │                   ├── MainApp.java
│   │   │                   ├── readersAndWriters
│   │   │                   │   ├── AccountingReader.java
│   │   │                   │   ├── AccountingWriter.java
│   │   │                   │   ├── BudgetReader.java
│   │   │                   │   ├── BudgetWriter.java
│   │   │                   │   ├── CategoryReader.java
│   │   │                   │   ├── CategoryWriter.java
│   │   │                   │   ├── DocumentReader.java
│   │   │                   │   ├── DocumentWriter.java
│   │   │                   │   ├── SettingsReader.java
│   │   │                   │   └── SettingsWriter.java
│   │   │                   ├── scenes
│   │   │                   │   ├── View.java
│   │   │                   │   └── ViewSwitcher.java
│   │   │                   └── utilities
│   │   │                       ├── BaeconDateFormatter.java
│   │   │                       └── BaeconFiles.java
│   │   └── resources
│   │       ├── edu
│   │       │   └── ntnu
│   │       │       └── idatt1002
│   │       │           └── baecon
│   │       │               ├── dataFiles
│   │       │               │   ├── accountingEntries
│   │       │               │   │   ├── 2023.01.csv
│   │       │               │   │   ├── 2023.02.csv
│   │       │               │   │   ├── 2023.03.csv
│   │       │               │   │   ├── 2023.04.csv
│   │       │               │   │   ├── 2023.05.csv
│   │       │               │   │   ├── 2023.06.csv
│   │       │               │   │   ├── 2023.07.csv
│   │       │               │   │   ├── 2023.08.csv
│   │       │               │   │   ├── 2023.09.csv
│   │       │               │   │   ├── 2023.10.csv
│   │       │               │   │   ├── 2023.11.csv
│   │       │               │   │   ├── 2023.12.csv
│   │       │               │   │   └── 2024.01.csv
│   │       │               │   ├── budgetEntries
│   │       │               │   │   ├── 2023.01.csv
│   │       │               │   │   ├── 2023.02.csv
│   │       │               │   │   ├── 2023.03.csv
│   │       │               │   │   ├── 2023.04.csv
│   │       │               │   │   ├── 2023.05.csv
│   │       │               │   │   ├── 2023.06.csv
│   │       │               │   │   ├── 2023.07.csv
│   │       │               │   │   ├── 2023.08.csv
│   │       │               │   │   ├── 2023.09.csv
│   │       │               │   │   ├── 2023.10.csv
│   │       │               │   │   ├── 2023.11.csv
│   │       │               │   │   ├── 2023.12.csv
│   │       │               │   │   ├── 2024.01.csv
│   │       │               │   │   ├── 2024.02.csv
│   │       │               │   │   └── 2024.03.csv
│   │       │               │   ├── categories.csv
│   │       │               │   ├── documents.csv
│   │       │               │   └── settings.ser
│   │       │               ├── documents
│   │       │               │   └── 100-ola-nordmann-gmail-com.pdf
│   │       │               └── scenes
│   │       │                   ├── aboutDialog.fxml
│   │       │                   ├── accountingPage.fxml
│   │       │                   ├── addAccountingEntry.fxml
│   │       │                   ├── addBudgetEntry.fxml
│   │       │                   ├── budgetPage.fxml
│   │       │                   ├── css
│   │       │                   │   ├── barChart.css
│   │       │                   │   └── dark-mode.css
│   │       │                   ├── dashboardPage.fxml
│   │       │                   ├── documentsPage.fxml
│   │       │                   ├── editAccountingEntry.fxml
│   │       │                   ├── generalLayout.fxml
│   │       │                   ├── img
│   │       │                   │   ├── logo-banner-transparent-01.png
│   │       │                   │   └── logo-transparent-no_title-02-100px.png
│   │       │                   ├── settingsCategoryDialog.fxml
│   │       │                   └── settingsPage.fxml
│   │       └── META-INF
│   │           └── MANIFEST.MF
│   └── test
│       ├── java
│       │   └── edu
│       │       └── ntnu
│       │           └── idatt1002
│       │               └── baecon
│       │                   ├── controller
│       │                   │   ├── AccountingEntriesControllerTest.java
│       │                   │   ├── BudgetEntriesControllerTest.java
│       │                   │   ├── CategoriesControllerTest.java
│       │                   │   ├── DocumentsControllerTest.java
│       │                   │   └── SettingsControllerTest.java
│       │                   ├── data
│       │                   │   ├── AccountingEntryTest.java
│       │                   │   ├── BudgetEntryTest.java
│       │                   │   ├── CategoryTest.java
│       │                   │   ├── DocumentTest.java
│       │                   │   └── SettingsTest.java
│       │                   ├── readersAndWriters
│       │                   │   ├── AccountingReaderTest.java
│       │                   │   ├── AccountingWriterTest.java
│       │                   │   ├── BudgetReaderTest.java
│       │                   │   ├── BudgetWriterTest.java
│       │                   │   ├── CategoryReaderTest.java
│       │                   │   ├── CategoryWriterTest.java
│       │                   │   ├── DocumentReaderTest.java
│       │                   │   ├── DocumentWriterTest.java
│       │                   │   ├── SettingsReaderTest.java
│       │                   │   └── SettingsWriterTest.java
│       │                   └── utilities
│       │                       ├── BaeconDateFormatterTest.java
│       │                       └── BaeconFilesTest.java
│       └── resources
│           └── edu
│               └── ntnu
│                   └── idatt1002
│                       └── baecon
│                           └── dataFiles
│                               ├── accountingEntries
│                               │   └── 2023.04.csv
│                               ├── budgetEntries
│                               │   └── 2023.04.csv
│                               ├── categories.csv
│                               ├── documents.csv
│                               └── settings.ser
└── target
    ├── classes
    │   ├── edu
    │   │   └── ntnu
    │   │       └── idatt1002
    │   │           └── baecon
    │   │               ├── BaeconApplication.class
    │   │               ├── components
    │   │               │   ├── ErrorAlert.class
    │   │               │   └── NumberAndPunctuationTextField.class
    │   │               ├── controller
    │   │               │   ├── AboutDialogController.class
    │   │               │   ├── AccountingEntriesController.class
    │   │               │   ├── AccountingPageController$1.class
    │   │               │   ├── AccountingPageController$2.class
    │   │               │   ├── AccountingPageController$3.class
    │   │               │   ├── AccountingPageController$4.class
    │   │               │   ├── AccountingPageController.class
    │   │               │   ├── AddAccountingEntryController.class
    │   │               │   ├── AddBudgetEntryController.class
    │   │               │   ├── BudgetEntriesController.class
    │   │               │   ├── BudgetPageController$1.class
    │   │               │   ├── BudgetPageController$2.class
    │   │               │   ├── BudgetPageController$3.class
    │   │               │   ├── BudgetPageController$4.class
    │   │               │   ├── BudgetPageController.class
    │   │               │   ├── CategoriesController.class
    │   │               │   ├── DashboardPageController$1.class
    │   │               │   ├── DashboardPageController$2.class
    │   │               │   ├── DashboardPageController$3.class
    │   │               │   ├── DashboardPageController$4.class
    │   │               │   ├── DashboardPageController$5.class
    │   │               │   ├── DashboardPageController$6.class
    │   │               │   ├── DashboardPageController$7.class
    │   │               │   ├── DashboardPageController$TableViewRow.class
    │   │               │   ├── DashboardPageController.class
    │   │               │   ├── DocumentsController.class
    │   │               │   ├── DocumentsPageController$1.class
    │   │               │   ├── DocumentsPageController.class
    │   │               │   ├── EditAccountingEntryController.class
    │   │               │   ├── GeneralLayoutController.class
    │   │               │   ├── SettingsCategoryDialogController.class
    │   │               │   ├── SettingsController.class
    │   │               │   └── SettingsPageController.class
    │   │               ├── data
    │   │               │   ├── AccountingEntry.class
    │   │               │   ├── BudgetEntry.class
    │   │               │   ├── Category.class
    │   │               │   ├── Document.class
    │   │               │   ├── Entry.class
    │   │               │   └── Settings.class
    │   │               ├── dataFiles
    │   │               │   ├── accountingEntries
    │   │               │   │   ├── 2023.01.csv
    │   │               │   │   ├── 2023.02.csv
    │   │               │   │   ├── 2023.03.csv
    │   │               │   │   ├── 2023.04.csv
    │   │               │   │   ├── 2023.05.csv
    │   │               │   │   ├── 2023.06.csv
    │   │               │   │   ├── 2023.07.csv
    │   │               │   │   ├── 2023.08.csv
    │   │               │   │   ├── 2023.09.csv
    │   │               │   │   ├── 2023.10.csv
    │   │               │   │   ├── 2023.11.csv
    │   │               │   │   ├── 2023.12.csv
    │   │               │   │   └── 2024.01.csv
    │   │               │   ├── budgetEntries
    │   │               │   │   ├── 2023.01.csv
    │   │               │   │   ├── 2023.02.csv
    │   │               │   │   ├── 2023.03.csv
    │   │               │   │   ├── 2023.04.csv
    │   │               │   │   ├── 2023.05.csv
    │   │               │   │   ├── 2023.06.csv
    │   │               │   │   ├── 2023.07.csv
    │   │               │   │   ├── 2023.08.csv
    │   │               │   │   ├── 2023.09.csv
    │   │               │   │   ├── 2023.10.csv
    │   │               │   │   ├── 2023.11.csv
    │   │               │   │   ├── 2023.12.csv
    │   │               │   │   ├── 2024.01.csv
    │   │               │   │   ├── 2024.02.csv
    │   │               │   │   └── 2024.03.csv
    │   │               │   ├── categories.csv
    │   │               │   ├── documents.csv
    │   │               │   └── settings.ser
    │   │               ├── documents
    │   │               │   └── 100-ola-nordmann-gmail-com.pdf
    │   │               ├── factory
    │   │               │   ├── AccountingEntriesControllerFactory.class
    │   │               │   ├── BudgetEntriesControllerFactory.class
    │   │               │   ├── CategoriesControllerFactory.class
    │   │               │   ├── DocumentsControllerFactory.class
    │   │               │   └── SettingsControllerFactory.class
    │   │               ├── listeners
    │   │               │   ├── AccountingEntriesChangeListener.class
    │   │               │   ├── BudgetEntriesChangeListener.class
    │   │               │   ├── CategoriesChangeListener.class
    │   │               │   ├── DocumentsChangeListener.class
    │   │               │   └── SettingsListener.class
    │   │               ├── MainApp.class
    │   │               ├── readersAndWriters
    │   │               │   ├── AccountingReader.class
    │   │               │   ├── AccountingWriter.class
    │   │               │   ├── BudgetReader.class
    │   │               │   ├── BudgetWriter.class
    │   │               │   ├── CategoryReader.class
    │   │               │   ├── CategoryWriter.class
    │   │               │   ├── DocumentReader.class
    │   │               │   ├── DocumentWriter.class
    │   │               │   ├── SettingsReader.class
    │   │               │   └── SettingsWriter.class
    │   │               ├── scenes
    │   │               │   ├── aboutDialog.fxml
    │   │               │   ├── accountingPage.fxml
    │   │               │   ├── addAccountingEntry.fxml
    │   │               │   ├── addBudgetEntry.fxml
    │   │               │   ├── budgetPage.fxml
    │   │               │   ├── css
    │   │               │   │   ├── barChart.css
    │   │               │   │   └── dark-mode.css
    │   │               │   ├── dashboardPage.fxml
    │   │               │   ├── documentsPage.fxml
    │   │               │   ├── editAccountingEntry.fxml
    │   │               │   ├── generalLayout.fxml
    │   │               │   ├── img
    │   │               │   │   ├── logo-banner-transparent-01.png
    │   │               │   │   └── logo-transparent-no_title-02-100px.png
    │   │               │   ├── settingsCategoryDialog.fxml
    │   │               │   ├── settingsPage.fxml
    │   │               │   ├── View.class
    │   │               │   └── ViewSwitcher.class
    │   │               ├── Text File.txt
    │   │               └── utilities
    │   │                   ├── BaeconDateFormatter.class
    │   │                   └── BaeconFiles.class
    │   └── META-INF
    │       └── MANIFEST.MF
    ├── generated-sources
    │   └── annotations
    └── maven-status
        └── maven-compiler-plugin
            └── compile
                └── default-compile
                    ├── createdFiles.lst
                    └── inputFiles.lst

96 directories, 389 files

```

</details>