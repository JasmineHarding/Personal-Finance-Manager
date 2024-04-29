public class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(String accountID, double balance, double interestRate) {
        super(accountID, balance);
        this.interestRate = interestRate;
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

    public void applyInterest() {
        double interest = this.balance * (interestRate / 100);
        this.balance += interest;
    }
}
