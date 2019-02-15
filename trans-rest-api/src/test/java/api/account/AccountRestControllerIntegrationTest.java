package api.account;

import api.BaseIntegrationTest;
import org.junit.Test;

import java.math.BigDecimal;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.with;
import static org.hamcrest.CoreMatchers.equalTo;

public class AccountRestControllerIntegrationTest extends BaseIntegrationTest {

    @Test
    public void shouldReturnCodeNotFoundWhenRequestingAnUnknownAccount() {
        get("/api/account/fakeId")
                .then()
                .statusCode(404);
    }

    @Test
    public void shouldSuccessfullyCreateAnAccount() {
        final String accountNumber = with().body(buildTestAccount(new BigDecimal("2500")))
                .when()
                .post("/api/account")
                .then()
                .statusCode(201)
                .extract()
                .path("accountNumber");

        get("/api/account/" + accountNumber)
                .then()
                .statusCode(200)
                .assertThat()
                .body("balance", equalTo(2500));
    }

    @Test
    public void shouldFailCreatingAnAccountWithNegativeBalance() {
        with().body(buildTestAccount(new BigDecimal("-2500")))
                .when()
                .post("/api/account")
                .then()
                .statusCode(400);
    }
}
