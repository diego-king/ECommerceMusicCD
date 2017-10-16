package ca.edu.uottawa.csi5380.database.agent.account;

import ca.edu.uottawa.csi5380.model.Account;

public interface AccountDao {

    void accountLogin(String username, String password);
    void createAccount(Account account);
    Account getAccount(String username, String password);

}
