package ca.edu.uottawa.csi5380.database.agent.account;

import ca.edu.uottawa.csi5380.database.agent.DataAgent;
import ca.edu.uottawa.csi5380.database.agent.utils.DataUtils;
import ca.edu.uottawa.csi5380.exception.RestDaoException;
import ca.edu.uottawa.csi5380.model.Account;
import ca.edu.uottawa.csi5380.model.Address;
import ca.edu.uottawa.csi5380.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountDaoImpl implements AccountDao {

    // SQL SELECT KEYS
    private static final String SQL_SELECT_LAST_INSERT_ID = "sql.select.last.insert.id";

    private static final String SQL_SELECT_CUSTOMER_BY_EMAIL = "sql.select.customer.by.email";
    private static final String SQL_SELECT_ADDRESSES_BY_ID = "sql.select.addresses.by.id";

    // SQL INSERT KEYS
    private static final String SQL_INSERT_CUSTOMER = "sql.insert.customer";
    private static final String SQL_INSERT_ADDRESS = "sql.insert.address";

    private final DataAgent dataAgent;

    @Autowired
    public AccountDaoImpl(DataAgent dataAgent) {
        this.dataAgent = dataAgent;
    }

    /**
     * Confirms Customer exists with given username (email), as well as confirming
     * username/password combination is correct.
     *
     * @param username
     * @param password
     */
    @Override
    public void accountLogin(String username, String password) {
        confirmCustomerWithCredentials(username, password);
    }

    @Override
    public void createAccount(Account account) {

        // Confirm there is no customer already with the given username (email)
        if (getCustomerByUsername(account.getCustomer().getEmail()) != null) {
            throw new RestDaoException("Customer account already exists with the given username.");
        }

        // Insert Customer
        insertCustomer(account.getCustomer());

        long customerId = getLastInsertId();

        // Insert addresses
        account.getAddressList().forEach(a -> insertAddress(a, customerId));

    }

    /**
     * Returns the Customer account information including shipping and billing addresses IFF:
     * - the given username (email) exists in the database AND
     * - the given username/password combination is correct.
     *
     * @param username
     * @param password
     */
    @Override
    public Account getAccount(String username, String password) {
        Customer c = confirmCustomerWithCredentials(username, password);
        return new Account(c, getAddressById(c.getId()));
    }

    /**
     * Get the list of addresses for the Customer using their ID.
     *
     * @param id - Customer ID
     * @return
     */
    private List<Address> getAddressById(long id) {
        return DataUtils.getAddressesFromResult(dataAgent.getQueryResult(SQL_SELECT_ADDRESSES_BY_ID, new Object[]{id}));
    }

    private Customer getCustomerByUsername(String username) {
        return DataUtils.getCustomerFromResult(dataAgent.getQueryResult(SQL_SELECT_CUSTOMER_BY_EMAIL, new Object[]{username}));
    }

    private void insertCustomer(Customer c) {
        dataAgent.executeSQL(SQL_INSERT_CUSTOMER, new Object[]{c.getFirstName(), c.getLastName(), c.getPassword(), c.getEmail()});
    }

    /**
     * Insert an address into the database given the address and customer ID foreign key.
     *
     * @param a          - Address to insert
     * @param customerId - Customer ID
     */
    private void insertAddress(Address a, long customerId) {
        dataAgent.executeSQL(SQL_INSERT_ADDRESS, new Object[]{customerId, a.getFullName(), a.getAddressLine1(),
                a.getAddressLine2(), a.getCity(), a.getProvince(), a.getCountry(), a.getZip(),
                a.getPhone(), a.getType().toString()});
    }

    /**
     * Verifies the given username & password match the given Customer's username
     * & password.
     *
     * @param username
     * @param password
     * @param c
     * @return
     */
    private boolean checkCredentials(String username, String password, Customer c) {
        return username.equals(c.getEmail()) && password.equals(c.getPassword());
    }

    /**
     * Returns the Customer information if the given username (email) exists, as well as confirming
     * username/password combination is correct.
     *
     * @param username
     * @param password
     */
    private Customer confirmCustomerWithCredentials(String username, String password) {

        Customer c = getCustomerByUsername(username);

        // Confirm there is a customer with the given username (email)
        if (c == null) throw new RestDaoException("Customer does not exist with the given username.");

        // Confirm username/password combination is correct
        if (!checkCredentials(username, password, c)) {
            throw new RestDaoException("Username/password combination is invalid.");
        }

        return c;
    }

    private long getLastInsertId() {
        return DataUtils.getLastInsertIdFromResult(dataAgent.getQueryResult(SQL_SELECT_LAST_INSERT_ID, new Object[]{}));
    }

}
