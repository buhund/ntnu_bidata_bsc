package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.enums.AccountType;
import no.ntnu.idatt2106.model.BankAccount;
import no.ntnu.idatt2106.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface for BankAccount repository
 * The repository communicates with the database and handles the BankAccount objects on behalf of the service layer
 */
@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    BankAccount findByAccountTypeAndUser(AccountType accountType, User user);
}
