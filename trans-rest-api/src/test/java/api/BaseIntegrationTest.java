package api;

import api.account.integration.AccountCreationRequest;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.math.BigDecimal;

import static api.App.start;
import static api.App.stop;
import static io.restassured.RestAssured.port;

public abstract class BaseIntegrationTest {

    @BeforeClass
    public static void setUp() {
        port = 7070;
        start();
    }

    @AfterClass
    public static void tearDown() {
        stop();
    }

    protected AccountCreationRequest buildTestAccount(final BigDecimal balance) {
        return new AccountCreationRequest(balance);
    }
}
