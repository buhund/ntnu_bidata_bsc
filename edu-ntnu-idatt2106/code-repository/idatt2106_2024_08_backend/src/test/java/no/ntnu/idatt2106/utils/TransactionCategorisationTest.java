package no.ntnu.idatt2106.utils;

import no.ntnu.idatt2106.enums.ProductCategory;
import no.ntnu.idatt2106.model.BankTransaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TransactionCategorisationTest {

  @Autowired
  private TransactionCategorisation transactionCategorisation;

  @Test
  void testCategoriseTransaction() {
    BankTransaction transaction = new BankTransaction();
    transaction.setDescription("REMA 1000");
    String category = transactionCategorisation.categoriseTransaction(transaction).name();
    assertEquals("GROCERIES", category);
  }

  @Test
  void testCategoriseAndSetTransactionCategory() {
    BankTransaction transaction = new BankTransaction();
    transaction.setDescription("Steam");
    transactionCategorisation.categorizeAndSetTransactionCategory(transaction);
    assertEquals(ProductCategory.ENTERTAINMENT, transaction.getCategory());
  }

  @Test
  void testCategoriseTransactionOther() {
    BankTransaction transaction = new BankTransaction();
    transaction.setDescription("Unknown company");
    String category = transactionCategorisation.categoriseTransaction(transaction).name();
    assertEquals("OTHER", category);
  }
}