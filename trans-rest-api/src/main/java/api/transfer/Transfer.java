package api.transfer;

import lombok.Getter;

import java.math.BigDecimal;

import static java.util.UUID.randomUUID;

@Getter
public class Transfer {

    public static enum TransferStatus {SUCCESSFUL, FAILED}

    private final String transferId;
    private final String senderAccountId;
    private final String recipientAccountId;
    private final BigDecimal amount;
    private final TransferStatus status;

    Transfer(final String senderAccountId, final String recipientAccountId, final BigDecimal amount, final TransferStatus status) {
        transferId = randomUUID().toString();
        this.senderAccountId = senderAccountId;
        this.recipientAccountId = recipientAccountId;
        this.amount = amount;
        this.status = status;
    }
}
