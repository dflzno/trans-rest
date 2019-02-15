package api.transfer;

import lombok.Getter;

import java.math.BigDecimal;

import static java.util.UUID.randomUUID;

@Getter
public class Transfer {

    private final String transferId;
    private final String senderAccountId;
    private final String recipientAccountId;
    private final BigDecimal amount;

    public Transfer(final String senderAccountId, final String recipientAccountId, final BigDecimal amount) {
        transferId = randomUUID().toString();
        this.senderAccountId = senderAccountId;
        this.recipientAccountId = recipientAccountId;
        this.amount = amount;
    }
}
