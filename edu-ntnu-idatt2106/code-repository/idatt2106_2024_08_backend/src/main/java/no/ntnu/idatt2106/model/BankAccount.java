package no.ntnu.idatt2106.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import no.ntnu.idatt2106.enums.AccountType;

import java.util.List;

/**
 * BankAccount is connected to a specific User.
 * It will contain a list of transactions from the bank account.
 */
@Entity
@Table(name = "bank_account")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountNumber;

    @Column
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    /**
     * Use user_id as foreign key to connect a bank account.
     * Multiple bank accounts can be connected to a single user.
     * A bank account can only be connected to one user.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Use account_number as foreign key to connect a transaction
     * A bank account can have multiple transactions.
     * A transaction can only be connected to one bank account.
     */
    @OneToMany(mappedBy = "bankAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "account-transaction")
    private List<BankTransaction> transactions;

    // TODO Implement logic for stripping of whitespace, dot and comma from account number.

    // Getters and setters

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
