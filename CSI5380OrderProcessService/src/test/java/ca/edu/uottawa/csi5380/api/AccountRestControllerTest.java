package ca.edu.uottawa.csi5380.api;

import ca.edu.uottawa.csi5380.Application;
import ca.edu.uottawa.csi5380.model.Account;
import ca.edu.uottawa.csi5380.model.builders.AccountBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

import static ca.edu.uottawa.csi5380.constant.RestPaths.ACCOUNT_BASE_REST_URL;
import static ca.edu.uottawa.csi5380.constant.RestPaths.ACCOUNT_CREATE_REST_URL;
import static ca.edu.uottawa.csi5380.constant.RestPaths.ACCOUNT_LOGIN_REST_URL;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for AccountRestController
 *
 * @author Kenny Byrd
 */
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Transactional
public class AccountRestControllerTest {

    private JacksonTester<Account> jacksonTester;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        ObjectMapper objectMapper = new ObjectMapper();
        // Possibly configure the mapper
        JacksonTester.initFields(this, objectMapper);
    }

    /**
     * Test accountLogin() at URL: /api/account/login using a valid username
     * and password combination.
     *
     */
    @Test
    public void testValidLogin() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(ACCOUNT_BASE_REST_URL + ACCOUNT_LOGIN_REST_URL)
                .queryParam("username", "andy_adler@gmail.com")
                .queryParam("password", "cGFzc3dvcmQ=");
        ResponseEntity<Void> resp = restTemplate.getForEntity(builder.build().encode().toUri(), Void.class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /**
     * Test accountLogin() at URL: /api/account/login using an invalid
     * password.
     *
     */
    @Test
    public void testInvalidLoginCredentials() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(ACCOUNT_BASE_REST_URL + ACCOUNT_LOGIN_REST_URL)
                .queryParam("username", "andy_adler@gmail.com")
                .queryParam("password", "wrong_password");
        ResponseEntity<Void> resp = restTemplate.getForEntity(builder.build().encode().toUri(), Void.class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    /**
     * Test accountLogin() at URL: /api/account/login using an unknown
     * username that is not in the database.
     *
     */
    @Test
    public void testInvalidLoginUsername() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(ACCOUNT_BASE_REST_URL + ACCOUNT_LOGIN_REST_URL)
                .queryParam("username", UUID.randomUUID()+"@gmail.com")
                .queryParam("password", "cGFzc3dvcmQ=");
        ResponseEntity<Void> resp = restTemplate.getForEntity(builder.build().encode().toUri(), Void.class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * Test createAccount() at URL: /api/account/create with valid JSON.
     *
     */
    @Test
    public void testCreateAccount() {
        Account a = new AccountBuilder().createAccount();
        a.getCustomer().setFirstName("John");
        a.getCustomer().setLastName("Smith");
        a.getCustomer().setEmail("jsmith@gmail.com");
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(ACCOUNT_BASE_REST_URL + ACCOUNT_CREATE_REST_URL);
        ResponseEntity<Void> resp = restTemplate.postForEntity(builder.build().encode().toUri(), a, Void.class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /**
     * Test createAccount() at URL: /api/account/create using a username
     * that already exists in the database.
     *
     */
    @Test
    public void testCreateAccountAlreadyExists() {
        Account a = new AccountBuilder().createAccount();
        a.getCustomer().setEmail("andy_adler@gmail.com");
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(ACCOUNT_BASE_REST_URL + ACCOUNT_CREATE_REST_URL);
        ResponseEntity<Void> resp = restTemplate.postForEntity(builder.build().encode().toUri(), a, Void.class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    /**
     * Test getAccount() at URL: /api/account and see if it returns
     * the expected result Account object.
     *
     * @throws Exception
     */
    @Test
    public void testGetAccount() throws Exception {

        String expected = "{\"customer\":{\"id\":3,\"firstName\":\"Andy\",\"lastName\":\"Adler\",\"email\":\"andy_adler@gmail.com\"," +
                "\"password\":\"cGFzc3dvcmQ=\",\"defaultShippingAddressId\":6,\"defaultBillingAddressId\":5},\"defaultAddressInfo\":" +
                "{\"billingAddress\":{\"id\":5,\"fullName\":\"Andy Adler\",\"addressLine1\":\"99 Main St.\",\"addressLine2\":\"\"," +
                "\"city\":\"Ottawa\",\"province\":\"ON\",\"country\":\"Canada\",\"zip\":\"K6E 9T5\",\"phone\":\"613-123-9568\"," +
                "\"type\":\"BILLING\"},\"shippingAddress\":{\"id\":6,\"fullName\":\"Andy Adler\",\"addressLine1\":\"99 Main St.\"," +
                "\"addressLine2\":\"\",\"city\":\"Ottawa\",\"province\":\"ON\",\"country\":\"Canada\",\"zip\":\"K6E 9T5\"," +
                "\"phone\":\"613-123-9568\",\"type\":\"SHIPPING\"}}}";

        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(ACCOUNT_BASE_REST_URL)
                .queryParam("username", "andy_adler@gmail.com")
                .queryParam("password", "cGFzc3dvcmQ=");

        ResponseEntity<Account> resp = restTemplate.getForEntity(builder.build().encode().toUri(), Account.class);
        Account result = resp.getBody();

        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(jacksonTester.parseObject(expected)).isEqualTo(result);

    }

}
