package api.transfer;

import api.BaseIntegrationTest;
import api.transfer.integration.TransferRequest;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static api.transfer.Transfer.TransferStatus.FAILED;
import static api.transfer.Transfer.TransferStatus.SUCCESSFUL;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;

public class TransferRestControllerIntegrationTest extends BaseIntegrationTest {

    private String senderAccountNumber;
    private String recipientAccountNumber;

    @Before
    public void populateSomeAccounts() {

        senderAccountNumber = with().body(buildTestAccount(new BigDecimal("3000")))
                .when()
                .post("/api/account")
                .then()
                .extract()
                .path("accountNumber");

        recipientAccountNumber = with().body(buildTestAccount(new BigDecimal("500")))
                .when()
                .post("/api/account")
                .then()
                .extract()
                .path("accountNumber");
    }

    @Test
    public void shouldSuccessfullyTransferTheGivenAmountFromSenderToRecipient() {
        with().body(buildTestTransfer(new BigDecimal("1500")))
                .when()
                .post("/api/transfer")
                .then()
                .statusCode(201)
                .assertThat()
                .body("error", nullValue())
                .body("transfer.status", equalTo(SUCCESSFUL.name()));

        get("/api/account/" + senderAccountNumber)
                .then()
                .statusCode(200)
                .assertThat()
                .body("balance", equalTo(1500));

        get("/api/account/" + recipientAccountNumber)
                .then()
                .statusCode(200)
                .assertThat()
                .body("balance", equalTo(2000));
    }

    @Test
    public void shouldFindTheRequestedTransfer() {
        final String transferId = with().body(buildTestTransfer(new BigDecimal("1500")))
                .when()
                .post("/api/transfer")
                .then()
                .statusCode(201)
                .extract()
                .path("transfer.transferId");

        get("/api/transfer/" + transferId)
                .then()
                .statusCode(200)
                .assertThat()
                .body("amount", equalTo(1500))
                .body("senderAccountId", equalTo(senderAccountNumber))
                .body("recipientAccountId", equalTo(recipientAccountNumber))
                .body("status", equalTo(SUCCESSFUL.name()));
    }

    @Test
    public void shouldFailToMakeTheTransferDueToInsufficientCreditInSenderAccount() {
        with().body(buildTestTransfer(new BigDecimal("4500")))
                .when()
                .post("/api/transfer")
                .then()
                .statusCode(400)
                .assertThat()
                .body("error", equalTo("Not enough funds"))
                .body("transfer.status", equalTo(FAILED.name()));

        get("/api/account/" + senderAccountNumber)
                .then()
                .statusCode(200)
                .assertThat()
                .body("balance", equalTo(3000));

        get("/api/account/" + recipientAccountNumber)
                .then()
                .statusCode(200)
                .assertThat()
                .body("balance", equalTo(500));
    }

    private TransferRequest buildTestTransfer(final BigDecimal amount) {
        return new TransferRequest(senderAccountNumber, recipientAccountNumber, amount);
    }

}
