package no.ntnu.idatt2106.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import no.ntnu.idatt2106.enums.ProductCategory;

import java.time.LocalDate;

/**
 * Class for creating bank transaction objects.
 * A transaction is a single event where money is moved from one account to another.
 * Transactions goes from one bank account to another.
 * A transaction is tied to one specific account number ID.
 * An account can have many transactions tied to it.
 */
@Entity
@Table(name = "bank_transaction")
public class BankTransaction {

    /**
     * Unique identifier for a given Bank Transaction.
     * Automatically generated.
     * Acts as primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private LocalDate transactionDate;

    @Column
    private String fromAccountNumber;

    @Column
    private String toAccountNumber;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column
    private ProductCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_number")
    @JsonBackReference(value = "account-transaction")
    private BankAccount bankAccount;

    // Setters and getters

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long id) {
        this.transactionId = id;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate date) {
        this.transactionDate = date;
    }

    public String getFromAccountNumber() {
        return fromAccountNumber;
    }

    public void setFromAccountNumber(String fromAccountNumber) {
        this.fromAccountNumber = fromAccountNumber;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber(String toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }
}
