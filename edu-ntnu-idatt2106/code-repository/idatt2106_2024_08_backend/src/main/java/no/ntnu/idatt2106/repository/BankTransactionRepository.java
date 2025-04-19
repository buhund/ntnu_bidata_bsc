package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.BankAccount;
import no.ntnu.idatt2106.model.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface for bank transaction
 * The repository communicates with the database and handles the BankTransaction objects on behalf of the service layer
 */
@Repository
public interface BankTransactionRepository extends JpaRepository<BankTransaction, Long> {
    List<BankTransaction> findByBankAccount(BankAccount bankAccount);
}
