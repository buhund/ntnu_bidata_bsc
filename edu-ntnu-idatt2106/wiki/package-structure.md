## Introduksjon
Prosjektets struktur av pakker, filer, mapper og avhengigheter er satt opp i henhold til retningslinjene for både Maven, Java, JavaDoc, Spring og REST.

## Pakkestruktur
Følgende trestrukturer viser en oversikt over pakkene i prosjektet. Pakker kan ses på som mapper som inneholder Java-filer basert på kategori, det vil si hvilken funksjon de har i systemets design.

### Hovedstruktur
<details>
    <summary>Klikk for å utvide eller kollapse kodeblokken</summary>

```
src/main/
├── java
│   └── no
│       └── ntnu
│           └── idatt2106
│               ├── config
│               │   ├── AppConfig.java
│               │   ├── CategorisationConfig.java
│               │   └── SecurityConfig.java
│               ├── controller
│               │   ├── BadgeController.java
│               │   ├── BankDataController.java
│               │   ├── ChallengeController.java
│               │   ├── CsvController.java
│               │   ├── GoalController.java
│               │   ├── LoginController.java
│               │   ├── NewsController.java
│               │   ├── TipsController.java
│               │   └── UserController.java
│               ├── enums
│               │   ├── AccountType.java
│               │   ├── BankHeader.java
│               │   ├── ChallengeType.java
│               │   ├── KnowledgeLevel.java
│               │   └── ProductCategory.java
│               ├── exceptions
│               │   └── RestResponseEntityExceptionHandler.java
│               ├── Main.java
│               ├── model
│               │   ├── Badge.java
│               │   ├── BankAccount.java
│               │   ├── BankTransaction.java
│               │   ├── Challenge.java
│               │   ├── Goal.java
│               │   ├── Overview.java
│               │   ├── Savings.java
│               │   ├── UserBadge.java
│               │   └── User.java
│               ├── repository
│               │   ├── BadgeRepository.java
│               │   ├── BankAccountRepository.java
│               │   ├── BankTransactionRepository.java
│               │   ├── ChallengeRepository.java
│               │   ├── GoalRepository.java
│               │   ├── UserBadgeRepository.java
│               │   └── UserRepository.java
│               ├── security
│               │   └── JWTAuthorizationFilter.java
│               ├── service
│               │   ├── BadgeAwardService.java
│               │   ├── BadgeService.java
│               │   ├── BankAccountService.java
│               │   ├── BankStatementParserService.java
│               │   ├── BankTransactionService.java
│               │   ├── ChallengeService.java
│               │   ├── GoalService.java
│               │   ├── JavaMailSenderService.java
│               │   ├── LoginService.java
│               │   ├── NewsService.java
│               │   ├── TipsService.java
│               │   ├── TransactionProcessingService.java
│               │   ├── UserAccountService.java
│               │   └── UserService.java
│               └── utils
│                   ├── CsvParser.java
│                   └── TransactionCategorisation.java
├── resources
│   ├── alternate-currencies
│   ├── application.properties
│   ├── badgeassets
│   │   ├── laceBadge1.png
│   │   ├── laceBadge2.png
│   │   ├── laceBadge3.png
│   │   ├── ribbonBadge1.png
│   │   ├── ribbonBadge2.png
│   │   ├── ribbonBadge3.png
│   │   ├── ribbonBadge4.png
│   │   ├── shieldBadge1.png
│   │   ├── shieldBadge2.png
│   │   ├── shieldBadge3.png
│   │   ├── shieldBadge4.png
│   │   ├── trophyBadge1.png
│   │   ├── trophyBadge2.png
│   │   └── trophyBadge3.png
│   ├── companies
│   ├── dummybankstatement-dnb.csv
│   ├── dummybankstatement-handelsbanken.csv
│   ├── dummybankstatement-nordea.csv
│   ├── idatt2105-32.idi.ntnu.no.p12
│   ├── tips-high
│   ├── tips-low
│   └── tips-medium
└── tree.txt

16 directories, 77 files
```
</details>

### Teststruktur
<details>
    <summary>Klikk for å utvide eller kollapse kodeblokken</summary>
```
src/test/
├── java
│   └── no
│       └── ntnu
│           └── idatt2106
│               ├── controller
│               │   ├── ChallengeControllerEndToEndTests.java
│               │   ├── CsvControllerEndToEndTests.java
│               │   ├── GoalControllerEndToEndTests.java
│               │   ├── LoginControllerEndToEndTests.java
│               │   └── UserControllerEndToEndTests.java
│               ├── repository
│               │   ├── GoalRepositoryIntegrationTests.java
│               │   └── UserRepositoryIntegrationTests.java
│               ├── service
│               │   ├── BadgeAwardServiceTest.java
│               │   ├── ChallengeServiceTest.java
│               │   ├── GoalServiceTest.java
│               │   ├── JavaMailSenderServiceTest.java
│               │   ├── LoginServiceTest.java
│               │   ├── TipsServiceTest.java
│               │   └── UserServiceTest.java
│               └── utils
│                   ├── CsvParserTest.java
│                   └── TransactionCategorisationTest.java
├── resources
│   ├── application.properties
│   ├── companies
│   ├── dummybankstatement-dnb.csv
│   ├── dummybankstatement-dnb-inn_pa_konto.csv
│   ├── dummybankstatement-dnb-no-content.csv
│   ├── dummybankstatement-dnb-ut_fra_konto.csv
│   ├── dummybankstatement-handelsbanken.csv
│   ├── dummybankstatement-nordea.csv
│   ├── tips-high
│   ├── tips-low
│   └── tips-medium
└── tree.txt

10 directories, 28 files
```
</details>


## Avhengigheter

Avhengigheter, slik de fremkommer i POM.xml:
- Spring Boot Starter Web
- Spring Boot Starter Test
- Spring Boot Starter Data JPA
- Spring Boot Starter Security
- MySQL Connector
- Jacoco
- Spring Boot Starter JSON
- JetBrains Annotations
- Simple Logging Facade for Java (SLF4J)
- HSQLDB
- Auth0 Java-JWT
- Jackson Databind
- Spring Boot Starter Mail
- Passay
- Greenmail-JUnit5
- SpringDoc OpenAPI Starter WebMVC
- Rometools

<details>
    <summary>Klikk for å utvide eller kollapse kodeblokken</summary>
```
<dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>3.2.5</version>
            <exclusions>
                <exclusion>
                    <groupId>ch.qos.logback</groupId>
                    <artifactId>logback-classic</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <version>3.2.5</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
            <version>3.2.5</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
            <version>3.2.5</version>
        </dependency>
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <version>8.3.0</version>
        </dependency>
        <dependency>
            <groupId>org.jacoco</groupId>
            <artifactId>jacoco-maven-plugin</artifactId>
            <version>0.8.12</version>
            <type>maven-plugin</type>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-json</artifactId>
            <version>3.2.5</version>
        </dependency>
        <dependency>
            <groupId>org.jetbrains</groupId>
            <artifactId>annotations</artifactId>
            <version>24.1.0</version>
            <scope>compile</scope>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-simple</artifactId>
            <version>2.0.13</version>
        </dependency>
        <dependency>
            <groupId>org.hsqldb</groupId>
            <artifactId>hsqldb</artifactId>
            <version>2.7.2</version>
        </dependency>
        <dependency>
            <groupId>com.auth0</groupId>
            <artifactId>java-jwt</artifactId>
            <version>4.4.0</version>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.17.0</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-mail</artifactId>
            <version>3.2.5</version>
        </dependency>
        <dependency>
            <groupId>org.passay</groupId>
            <artifactId>passay</artifactId>
            <version>1.6.4</version>
        </dependency>
        <dependency>
            <groupId>com.icegreen</groupId>
            <artifactId>greenmail-junit5</artifactId>
            <version>2.1.0-alpha-4</version>
        </dependency>
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
            <version>2.5.0</version>
        </dependency>
        <dependency>
            <groupId>com.rometools</groupId>
            <artifactId>rome</artifactId>
            <version>2.1.0</version>
        </dependency>
    </dependencies>
```
</details>


## Plugins

Plugins, slik de fremkommer i POM.xml:
- Maven Clean
- Maven Compiler
- Maven Deply
- Maven Install
- Maven Jacoco
- Maven Jar
- Macen Resources
- Maven Shade
- Maven Site
- Maven Spring Boot
- Maven Surefire

<details>
    <summary>Klikk for å utvide eller kollapse kodeblokken</summary>
```
<plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>3.2.0</version>
            </plugin>
            <plugin>
                <groupId>org.jacoco</groupId>
                <artifactId>jacoco-maven-plugin</artifactId>
                <version>0.8.12</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>prepare-agent</goal>
                        </goals>
                    </execution>
                    <!-- attached to Maven test phase -->
                    <execution>
                        <id>report</id>
                        <phase>test</phase>
                        <goals>
                            <goal>report</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.13.0</version>
                <configuration>
                    <release>21</release>
                    <compilerArgs>
                        <arg>-parameters</arg>
                    </compilerArgs>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <version>3.5.2</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                        <configuration>
                            <shadedArtifactAttached>true</shadedArtifactAttached>
                            <transformers>
                                <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                                    <mainClass>no.ntnu.idatt2106.Main</mainClass>
                                </transformer>
                            </transformers>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
```
</details>

## Prosjektets fulle filstruktur

Prosjektets fil- og mappestruktur, som vist ved `tree` i Linux CLI:

<details>
    <summary>Klikk for å utvide eller kollapse kodeblokken</summary>

```
(root-project-directory).
├── dependency-reduced-pom.xml
├── pom.xml
├── README.md
├── src
│   ├── main
│   │   ├── java
│   │   │   └── no
│   │   │       └── ntnu
│   │   │           └── idatt2106
│   │   │               ├── config
│   │   │               │   ├── AppConfig.java
│   │   │               │   ├── CategorisationConfig.java
│   │   │               │   └── SecurityConfig.java
│   │   │               ├── controller
│   │   │               │   ├── BadgeController.java
│   │   │               │   ├── BankDataController.java
│   │   │               │   ├── ChallengeController.java
│   │   │               │   ├── CsvController.java
│   │   │               │   ├── GoalController.java
│   │   │               │   ├── LoginController.java
│   │   │               │   ├── NewsController.java
│   │   │               │   ├── TipsController.java
│   │   │               │   └── UserController.java
│   │   │               ├── enums
│   │   │               │   ├── AccountType.java
│   │   │               │   ├── BankHeader.java
│   │   │               │   ├── ChallengeType.java
│   │   │               │   ├── KnowledgeLevel.java
│   │   │               │   └── ProductCategory.java
│   │   │               ├── exceptions
│   │   │               │   └── RestResponseEntityExceptionHandler.java
│   │   │               ├── Main.java
│   │   │               ├── model
│   │   │               │   ├── Badge.java
│   │   │               │   ├── BankAccount.java
│   │   │               │   ├── BankTransaction.java
│   │   │               │   ├── Challenge.java
│   │   │               │   ├── Goal.java
│   │   │               │   ├── Overview.java
│   │   │               │   ├── Savings.java
│   │   │               │   ├── UserBadge.java
│   │   │               │   └── User.java
│   │   │               ├── repository
│   │   │               │   ├── BadgeRepository.java
│   │   │               │   ├── BankAccountRepository.java
│   │   │               │   ├── BankTransactionRepository.java
│   │   │               │   ├── ChallengeRepository.java
│   │   │               │   ├── GoalRepository.java
│   │   │               │   ├── UserBadgeRepository.java
│   │   │               │   └── UserRepository.java
│   │   │               ├── security
│   │   │               │   └── JWTAuthorizationFilter.java
│   │   │               ├── service
│   │   │               │   ├── BadgeAwardService.java
│   │   │               │   ├── BadgeService.java
│   │   │               │   ├── BankAccountService.java
│   │   │               │   ├── BankStatementParserService.java
│   │   │               │   ├── BankTransactionService.java
│   │   │               │   ├── ChallengeService.java
│   │   │               │   ├── GoalService.java
│   │   │               │   ├── JavaMailSenderService.java
│   │   │               │   ├── LoginService.java
│   │   │               │   ├── NewsService.java
│   │   │               │   ├── TipsService.java
│   │   │               │   ├── TransactionProcessingService.java
│   │   │               │   ├── UserAccountService.java
│   │   │               │   └── UserService.java
│   │   │               └── utils
│   │   │                   ├── CsvParser.java
│   │   │                   └── TransactionCategorisation.java
│   │   └── resources
│   │       ├── alternate-currencies
│   │       ├── application.properties
│   │       ├── badgeassets
│   │       │   ├── laceBadge1.png
│   │       │   ├── laceBadge2.png
│   │       │   ├── laceBadge3.png
│   │       │   ├── ribbonBadge1.png
│   │       │   ├── ribbonBadge2.png
│   │       │   ├── ribbonBadge3.png
│   │       │   ├── ribbonBadge4.png
│   │       │   ├── shieldBadge1.png
│   │       │   ├── shieldBadge2.png
│   │       │   ├── shieldBadge3.png
│   │       │   ├── shieldBadge4.png
│   │       │   ├── trophyBadge1.png
│   │       │   ├── trophyBadge2.png
│   │       │   └── trophyBadge3.png
│   │       ├── companies
│   │       ├── dummybankstatement-dnb.csv
│   │       ├── dummybankstatement-handelsbanken.csv
│   │       ├── dummybankstatement-nordea.csv
│   │       ├── idatt2105-32.idi.ntnu.no.p12
│   │       ├── tips-high
│   │       ├── tips-low
│   │       └── tips-medium
│   └── test
│       ├── java
│       │   └── no
│       │       └── ntnu
│       │           └── idatt2106
│       │               ├── controller
│       │               │   ├── ChallengeControllerEndToEndTests.java
│       │               │   ├── CsvControllerEndToEndTests.java
│       │               │   ├── GoalControllerEndToEndTests.java
│       │               │   ├── LoginControllerEndToEndTests.java
│       │               │   └── UserControllerEndToEndTests.java
│       │               ├── repository
│       │               │   ├── GoalRepositoryIntegrationTests.java
│       │               │   └── UserRepositoryIntegrationTests.java
│       │               ├── service
│       │               │   ├── BadgeAwardServiceTest.java
│       │               │   ├── ChallengeServiceTest.java
│       │               │   ├── GoalServiceTest.java
│       │               │   ├── JavaMailSenderServiceTest.java
│       │               │   ├── LoginServiceTest.java
│       │               │   ├── TipsServiceTest.java
│       │               │   └── UserServiceTest.java
│       │               └── utils
│       │                   ├── CsvParserTest.java
│       │                   └── TransactionCategorisationTest.java
│       └── resources
│           ├── application.properties
│           ├── companies
│           ├── dummybankstatement-dnb.csv
│           ├── dummybankstatement-dnb-inn_pa_konto.csv
│           ├── dummybankstatement-dnb-no-content.csv
│           ├── dummybankstatement-dnb-ut_fra_konto.csv
│           ├── dummybankstatement-handelsbanken.csv
│           ├── dummybankstatement-nordea.csv
│           ├── tips-high
│           ├── tips-low
│           └── tips-medium
├── target
│   ├── classes
│   │   ├── alternate-currencies
│   │   ├── application.properties
│   │   ├── badgeassets
│   │   │   ├── laceBadge1.png
│   │   │   ├── laceBadge2.png
│   │   │   ├── laceBadge3.png
│   │   │   ├── ribbonBadge1.png
│   │   │   ├── ribbonBadge2.png
│   │   │   ├── ribbonBadge3.png
│   │   │   ├── ribbonBadge4.png
│   │   │   ├── shieldBadge1.png
│   │   │   ├── shieldBadge2.png
│   │   │   ├── shieldBadge3.png
│   │   │   ├── shieldBadge4.png
│   │   │   ├── trophyBadge1.png
│   │   │   ├── trophyBadge2.png
│   │   │   └── trophyBadge3.png
│   │   ├── companies
│   │   ├── dummybankstatement-dnb.csv
│   │   ├── dummybankstatement-handelsbanken.csv
│   │   ├── dummybankstatement-nordea.csv
│   │   ├── idatt2105-32.idi.ntnu.no.p12
│   │   ├── no
│   │   │   └── ntnu
│   │   │       └── idatt2106
│   │   │           ├── config
│   │   │           │   ├── AppConfig.class
│   │   │           │   ├── CategorisationConfig.class
│   │   │           │   └── SecurityConfig.class
│   │   │           ├── controller
│   │   │           │   ├── BadgeController.class
│   │   │           │   ├── BankDataController.class
│   │   │           │   ├── ChallengeController.class
│   │   │           │   ├── CsvController.class
│   │   │           │   ├── GoalController.class
│   │   │           │   ├── LoginController.class
│   │   │           │   ├── NewsController.class
│   │   │           │   ├── TipsController.class
│   │   │           │   └── UserController.class
│   │   │           ├── enums
│   │   │           │   ├── AccountType.class
│   │   │           │   ├── BankHeader.class
│   │   │           │   ├── ChallengeType.class
│   │   │           │   ├── KnowledgeLevel.class
│   │   │           │   └── ProductCategory.class
│   │   │           ├── exceptions
│   │   │           │   └── RestResponseEntityExceptionHandler.class
│   │   │           ├── Main.class
│   │   │           ├── model
│   │   │           │   ├── Badge.class
│   │   │           │   ├── BankAccount.class
│   │   │           │   ├── BankTransaction.class
│   │   │           │   ├── Challenge.class
│   │   │           │   ├── Goal.class
│   │   │           │   ├── Overview.class
│   │   │           │   ├── Savings.class
│   │   │           │   ├── UserBadge.class
│   │   │           │   └── User.class
│   │   │           ├── repository
│   │   │           │   ├── BadgeRepository.class
│   │   │           │   ├── BankAccountRepository.class
│   │   │           │   ├── BankTransactionRepository.class
│   │   │           │   ├── ChallengeRepository.class
│   │   │           │   ├── GoalRepository.class
│   │   │           │   ├── UserBadgeRepository.class
│   │   │           │   └── UserRepository.class
│   │   │           ├── security
│   │   │           │   └── JWTAuthorizationFilter.class
│   │   │           ├── service
│   │   │           │   ├── BadgeAwardService.class
│   │   │           │   ├── BadgeService.class
│   │   │           │   ├── BankAccountService.class
│   │   │           │   ├── BankStatementParserService.class
│   │   │           │   ├── BankTransactionService$1.class
│   │   │           │   ├── BankTransactionService.class
│   │   │           │   ├── ChallengeService$1.class
│   │   │           │   ├── ChallengeService.class
│   │   │           │   ├── GoalService.class
│   │   │           │   ├── JavaMailSenderService.class
│   │   │           │   ├── LoginService$1.class
│   │   │           │   ├── LoginService.class
│   │   │           │   ├── NewsService.class
│   │   │           │   ├── TipsService$1.class
│   │   │           │   ├── TipsService.class
│   │   │           │   ├── TransactionProcessingService.class
│   │   │           │   ├── UserAccountService.class
│   │   │           │   └── UserService.class
│   │   │           └── utils
│   │   │               ├── CsvParser.class
│   │   │               └── TransactionCategorisation.class
│   │   ├── tips-high
│   │   ├── tips-low
│   │   └── tips-medium
│   ├── generated-sources
│   │   └── annotations
│   ├── generated-test-sources
│   │   └── test-annotations
│   ├── jacoco.exec
│   ├── maven-status
│   │   └── maven-compiler-plugin
│   │       ├── compile
│   │       │   └── default-compile
│   │       │       ├── createdFiles.lst
│   │       │       └── inputFiles.lst
│   │       └── testCompile
│   │           └── default-testCompile
│   │               ├── createdFiles.lst
│   │               └── inputFiles.lst
│   ├── site
│   │   └── jacoco
│   │       ├── index.html
│   │       ├── jacoco.csv
│   │       ├── jacoco-resources
│   │       │   ├── branchfc.gif
│   │       │   ├── branchnc.gif
│   │       │   ├── branchpc.gif
│   │       │   ├── bundle.gif
│   │       │   ├── class.gif
│   │       │   ├── down.gif
│   │       │   ├── greenbar.gif
│   │       │   ├── group.gif
│   │       │   ├── method.gif
│   │       │   ├── package.gif
│   │       │   ├── prettify.css
│   │       │   ├── prettify.js
│   │       │   ├── redbar.gif
│   │       │   ├── report.css
│   │       │   ├── report.gif
│   │       │   ├── session.gif
│   │       │   ├── sort.gif
│   │       │   ├── sort.js
│   │       │   ├── source.gif
│   │       │   └── up.gif
│   │       ├── jacoco-sessions.html
│   │       ├── jacoco.xml
│   │       ├── no.ntnu.idatt2106
│   │       │   ├── index.html
│   │       │   ├── index.source.html
│   │       │   ├── Main.html
│   │       │   └── Main.java.html
│   │       ├── no.ntnu.idatt2106.config
│   │       │   ├── AppConfig.html
│   │       │   ├── AppConfig.java.html
│   │       │   ├── CategorisationConfig.html
│   │       │   ├── CategorisationConfig.java.html
│   │       │   ├── index.html
│   │       │   ├── index.source.html
│   │       │   ├── SecurityConfig.html
│   │       │   └── SecurityConfig.java.html
│   │       ├── no.ntnu.idatt2106.controller
│   │       │   ├── BadgeController.html
│   │       │   ├── BadgeController.java.html
│   │       │   ├── BankDataController.html
│   │       │   ├── BankDataController.java.html
│   │       │   ├── ChallengeController.html
│   │       │   ├── ChallengeController.java.html
│   │       │   ├── CsvController.html
│   │       │   ├── CsvController.java.html
│   │       │   ├── GoalController.html
│   │       │   ├── GoalController.java.html
│   │       │   ├── index.html
│   │       │   ├── index.source.html
│   │       │   ├── LoginController.html
│   │       │   ├── LoginController.java.html
│   │       │   ├── NewsController.html
│   │       │   ├── NewsController.java.html
│   │       │   ├── TipsController.html
│   │       │   ├── TipsController.java.html
│   │       │   ├── UserController.html
│   │       │   └── UserController.java.html
│   │       ├── no.ntnu.idatt2106.enums
│   │       │   ├── AccountType.html
│   │       │   ├── AccountType.java.html
│   │       │   ├── BankHeader.html
│   │       │   ├── BankHeader.java.html
│   │       │   ├── ChallengeType.html
│   │       │   ├── ChallengeType.java.html
│   │       │   ├── index.html
│   │       │   ├── index.source.html
│   │       │   ├── Level.html
│   │       │   ├── Level.java.html
│   │       │   ├── ProductCategory.html
│   │       │   └── ProductCategory.java.html
│   │       ├── no.ntnu.idatt2106.exceptions
│   │       │   ├── index.html
│   │       │   ├── index.source.html
│   │       │   ├── RestResponseEntityExceptionHandler.html
│   │       │   └── RestResponseEntityExceptionHandler.java.html
│   │       ├── no.ntnu.idatt2106.model
│   │       │   ├── Badge.html
│   │       │   ├── Badge.java.html
│   │       │   ├── BankAccount.html
│   │       │   ├── BankAccount.java.html
│   │       │   ├── BankTransaction.html
│   │       │   ├── BankTransaction.java.html
│   │       │   ├── Challenge.html
│   │       │   ├── Challenge.java.html
│   │       │   ├── Goal.html
│   │       │   ├── Goal.java.html
│   │       │   ├── index.html
│   │       │   ├── index.source.html
│   │       │   ├── Overview.html
│   │       │   ├── Overview.java.html
│   │       │   ├── Savings.html
│   │       │   ├── Savings.java.html
│   │       │   ├── UserBadge.html
│   │       │   ├── UserBadge.java.html
│   │       │   ├── User.html
│   │       │   └── User.java.html
│   │       ├── no.ntnu.idatt2106.security
│   │       │   ├── index.html
│   │       │   ├── index.source.html
│   │       │   ├── JWTAuthorizationFilter.html
│   │       │   └── JWTAuthorizationFilter.java.html
│   │       ├── no.ntnu.idatt2106.service
│   │       │   ├── BadgeService.html
│   │       │   ├── BadgeService.java.html
│   │       │   ├── BankAccountService.html
│   │       │   ├── BankAccountService.java.html
│   │       │   ├── BankProcessorService.html
│   │       │   ├── BankProcessorService.java.html
│   │       │   ├── BankStatementParserService.html
│   │       │   ├── BankStatementParserService.java.html
│   │       │   ├── BankTransactionService.html
│   │       │   ├── BankTransactionService.java.html
│   │       │   ├── ChallengeService.html
│   │       │   ├── ChallengeService.java.html
│   │       │   ├── GoalService.html
│   │       │   ├── GoalService.java.html
│   │       │   ├── index.html
│   │       │   ├── index.source.html
│   │       │   ├── JavaMailSenderService.html
│   │       │   ├── JavaMailSenderService.java.html
│   │       │   ├── LoginService$1.html
│   │       │   ├── LoginService.html
│   │       │   ├── LoginService.java.html
│   │       │   ├── NewsService.html
│   │       │   ├── NewsService.java.html
│   │       │   ├── TipsService.html
│   │       │   ├── TipsService.java.html
│   │       │   ├── UserService.html
│   │       │   └── UserService.java.html
│   │       └── no.ntnu.idatt2106.utils
│   │           ├── CsvParser.html
│   │           ├── CsvParser.java.html
│   │           ├── index.html
│   │           ├── index.source.html
│   │           ├── TransactionCategorisation.html
│   │           └── TransactionCategorisation.java.html
│   └── test-classes
│       ├── application.properties
│       ├── companies
│       ├── dummybankstatement-dnb.csv
│       ├── dummybankstatement-dnb-inn_pa_konto.csv
│       ├── dummybankstatement-dnb-no-content.csv
│       ├── dummybankstatement-dnb-ut_fra_konto.csv
│       ├── dummybankstatement-handelsbanken.csv
│       ├── dummybankstatement-nordea.csv
│       ├── no
│       │   └── ntnu
│       │       └── idatt2106
│       │           ├── controller
│       │           │   ├── ChallengeControllerEndToEndTests.class
│       │           │   ├── CsvControllerEndToEndTests.class
│       │           │   ├── GoalControllerEndToEndTests.class
│       │           │   ├── LoginControllerEndToEndTests.class
│       │           │   └── UserControllerEndToEndTests.class
│       │           ├── repository
│       │           │   ├── GoalRepositoryIntegrationTests.class
│       │           │   └── UserRepositoryIntegrationTests.class
│       │           ├── service
│       │           │   ├── ChallengeServiceTest.class
│       │           │   ├── GoalServiceTest.class
│       │           │   ├── JavaMailSenderServiceTest.class
│       │           │   ├── LoginServiceTest.class
│       │           │   ├── TipsServiceTest$1.class
│       │           │   ├── TipsServiceTest.class
│       │           │   └── UserServiceTest.class
│       │           └── utils
│       │               ├── CsvParserTest.class
│       │               └── TransactionCategorisationTest.class
│       ├── tips-high
│       ├── tips-low
│       └── tips-medium
├── team8-sparesti-backend.iml
└── tree.txt

73 directories, 349 files
```
</details>