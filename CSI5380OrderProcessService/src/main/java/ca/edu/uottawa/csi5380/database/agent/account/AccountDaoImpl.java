package ca.edu.uottawa.csi5380.database.agent.account;

import ca.edu.uottawa.csi5380.database.agent.DataAgent;
import ca.edu.uottawa.csi5380.database.agent.utils.DataUtils;
import ca.edu.uottawa.csi5380.exception.RestDaoException;
import ca.edu.uottawa.csi5380.exception.UserNotFoundException;
import ca.edu.uottawa.csi5380.model.Account;
import ca.edu.uottawa.csi5380.model.Address;
import ca.edu.uottawa.csi5380.model.AddressInfo;
import ca.edu.uottawa.csi5380.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDaoImpl implements AccountDao {

    // SQL SELECT KEYS
    private static final String SQL_SELECT_LAST_INSERT_ID = "sql.select.last.insert.id";

    private static final String SQL_SELECT_CUSTOMER_BY_EMAIL = "sql.select.customer.by.email";
    private static final String SQL_SELECT_ADDRESS_BY_ID = "sql.select.address.by.id";

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

        // Insert shipping address
        insertAddress(account.getDefaultAddressInfo().getShippingAddress());
        long shippingId = getLastInsertId();

        // Insert billing address
        insertAddress(account.getDefaultAddressInfo().getBillingAddress());
        long billingId = getLastInsertId();

        // Insert Customer
        insertCustomer(account.getCustomer(), shippingId, billingId);

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
        // Use default Customer shipping and billing address IDs to get address info
        return new Account(c, new AddressInfo(
                getAddressById(c.getDefaultBillingAddressId()),
                getAddressById(c.getDefaultShippingAddressId())));
    }

    /**
     * Get an address by it's ID from the database.
     *
     * @param id - Address ID
     * @return
     */
    private Address getAddressById(long id) {
        return DataUtils.getAddressFromResult(dataAgent.getQueryResult(SQL_SELECT_ADDRESS_BY_ID, new Object[]{id}));
    }

    private Customer getCustomerByUsername(String username) {
        return DataUtils.getCustomerFromResult(dataAgent.getQueryResult(SQL_SELECT_CUSTOMER_BY_EMAIL, new Object[]{username}));
    }

    /**
     * Insert a new Customer into the database with the default billing
     * and shipping foreign keys.
     *
     * @param c
     * @param shippingId
     * @param billingId
     */
    private void insertCustomer(Customer c, long shippingId, long billingId) {
        dataAgent.executeSQL(SQL_INSERT_CUSTOMER, new Object[]{c.getFirstName(), c.getLastName(),
                c.getPassword(), c.getEmail(), shippingId, billingId});
    }

    /**
     * Insert an address into the database given the address.
     *
     * @param a - Address to insert
     */
    private void insertAddress(Address a) {
        dataAgent.executeSQL(SQL_INSERT_ADDRESS, new Object[]{a.getFullName(), a.getAddressLine1(),
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
        if (c == null) throw new UserNotFoundException("Customer does not exist with the given username.");

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
