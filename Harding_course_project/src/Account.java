public abstract class Account {
    protected String accountID;
    protected double balance;

    public Account(String accountID, double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative.");
        }
        this.accountID = accountID;
        this.balance = balance;
    }

    public String getAccountID() {
        return this.accountID;
    }

    public abstract void deposit(double amount);

    public abstract void withdraw(double amount) throws Exception;

    public double getBalance() {
        return this.balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountID='" + accountID + '\'' +
                ", balance=" + balance +
                '}';
    }
}
