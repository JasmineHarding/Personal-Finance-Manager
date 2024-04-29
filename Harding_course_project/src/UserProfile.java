import java.util.ArrayList;
import java.util.List;

public class UserProfile {
    private List<Account> accounts;

    public UserProfile() {
        this.accounts = new ArrayList<>();
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public void removeAccount(Account account) {
        accounts.remove(account);
    }

    public List<Account> getAccounts() {
        return new ArrayList<>(accounts);
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
