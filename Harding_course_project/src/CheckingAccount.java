public class CheckingAccount extends Account {
    public CheckingAccount(String accountID, double balance) {
        super(accountID, balance);
    }

    @Override
    public void deposit(double amount) {
        this.balance += amount;
    }

    @Override
    public void withdraw(double amount) throws Exception {
        if (amount > this.balance) {
            throw new Exception("Insufficient funds for withdrawal.");
        }
        this.balance -= amount;
    }
}
