import java.time.LocalDate;

public class Transaction {
    private String transactionID;
    private String accountID;
    private double amount;
    private LocalDate date;
    private String type;

    public Transaction(String transactionID, String accountID, double amount, LocalDate date, String type) {
        this.transactionID = transactionID;
        this.accountID = accountID;
        this.amount = amount;
        this.date = date;
        this.type = type;
    }

    // Getter methods
    public String getTransactionID() {
        return transactionID;
    }

    public String getAccountID() {
        return accountID;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Transaction{" +
               "transactionID='" + transactionID + '\'' +
               ", accountID='" + accountID + '\'' +
               ", amount=" + amount +
               ", date=" + date +
               ", type='" + type + '\'' +
               '}';
    }
}
