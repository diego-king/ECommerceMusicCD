package ca.edu.uottawa.csi5380.api;

import ca.edu.uottawa.csi5380.model.Account;
import ca.edu.uottawa.csi5380.service.AccountService;
import io.swagger.annotations.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static ca.edu.uottawa.csi5380.constant.RestPaths.ACCOUNT_BASE_REST_URL;
import static ca.edu.uottawa.csi5380.constant.RestPaths.ACCOUNT_CREATE_REST_URL;
import static ca.edu.uottawa.csi5380.constant.RestPaths.ACCOUNT_LOGIN_REST_URL;

/**
 * Provides REST services for account related queries.
 *
 * @author Kenny Byrd
 */
@RestController
@RequestMapping(ACCOUNT_BASE_REST_URL)
@Api(value = ACCOUNT_BASE_REST_URL)
public class AccountRestController {

    private static final Logger LOGGER = LogManager.getLogger(OrderRestController.class);

    private final AccountService accountService;

    @Autowired
    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ApiOperation(value = "Confirms user login.",
            notes = "Given the username and password, this method will determine if an account " +
                    "in the database exists with these exact credentials.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Login Successful."),
            @ApiResponse(code = 400, message = "Username/password does not match."),
            @ApiResponse(code = 404, message = "User account not found."),
            @ApiResponse(code = 500, message = "Database or server error occurred.")
    })
    @RequestMapping(value = ACCOUNT_LOGIN_REST_URL, method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void accountLogin(@ApiParam(value = "Customer's email address", required = true) @RequestParam("username") String username,
                             @ApiParam(value = "Customer's password", required = true) @RequestParam("password") String password) {
        LOGGER.info(String.format("accountLogin() called with username %s and password %s", username, password));
        accountService.accountLogin(username, password);
    }

    @ApiOperation(value = "Creates a new customer account.",
            notes = "Creates a new account using the Customer information (email, first, last name, password), " +
                    "and stores default billing/shipping address. Fails if the email (account name) already exists. " +
                    "IMPORTANT: Addresses do not need IDs and Customer does not need default shipping IDs.")
    @RequestMapping(value = ACCOUNT_CREATE_REST_URL, method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Account created successfully."),
            @ApiResponse(code = 400, message = "Account already exists with username."),
            @ApiResponse(code = 500, message = "Database or server error occurred.")
    })
    @ResponseStatus(value = HttpStatus.OK)
    public void createAccount(@ApiParam(value = "New Customer information", required = true) @RequestBody Account account) {
        LOGGER.info(String.format("createAccount() called with params: %s", account.toString()));
        accountService.createAccount(account);
    }

    @ApiOperation(value = "Get a customer account.",
            notes = "Returns account information containing the Customer and default shipping/billing address.",
            response = Account.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved account."),
            @ApiResponse(code = 400, message = "Username/password does not match."),
            @ApiResponse(code = 404, message = "User account not found."),
            @ApiResponse(code = 500, message = "Database or server error occurred.")
    })
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Account getAccount(@ApiParam(value = "Customer's email address", required = true) @RequestParam("username") String username,
                              @ApiParam(value = "Customer's password", required = true) @RequestParam("password") String password) {
        LOGGER.info(String.format("getAccount() called with username %s and password %s", username, password));
        return accountService.getAccount(username, password);
    }

}
