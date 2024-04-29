import java.util.ArrayList;

public class FinanceManager implements FinancialCalculator {
    @Override
    public double calculateMonthlySavings(double income, double expenses) {
        return income - expenses; // Returns the difference between income and expenses as savings
    }

    @Override
    public double calculateExpenses(ArrayList<Transaction> transactions) {
        double totalExpenses = 0;
        for (Transaction transaction : transactions) {
            if ("expense".equalsIgnoreCase(transaction.getType())) {  // Using getType() instead of direct field access
                totalExpenses += transaction.getAmount();  // Using getAmount() instead of direct field access
            }
        }
        return totalExpenses;
    }
}
