package ca.edu.uottawa.csi5380.service;

import ca.edu.uottawa.csi5380.database.agent.account.AccountDao;
import ca.edu.uottawa.csi5380.database.agent.account.AccountDaoImpl;
import ca.edu.uottawa.csi5380.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService implements AccountDao {

    private final AccountDaoImpl accountDaoImpl;

    @Autowired
    public AccountService(AccountDaoImpl accountDaoImpl) {
        this.accountDaoImpl = accountDaoImpl;
    }

    @Override
    @Transactional(readOnly = true)
    public void accountLogin(String username, String password) {
        accountDaoImpl.accountLogin(username, password);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createAccount(Account account) {
        accountDaoImpl.createAccount(account);
    }

    @Override
    @Transactional(readOnly = true)
    public Account getAccount(String username, String password) {
        return accountDaoImpl.getAccount(username, password);
    }

}
