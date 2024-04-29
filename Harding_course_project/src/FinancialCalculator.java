import java.util.ArrayList;

public interface FinancialCalculator {
    double calculateMonthlySavings(double income, double expenses);
    double calculateExpenses(ArrayList<Transaction> transactions);
}
