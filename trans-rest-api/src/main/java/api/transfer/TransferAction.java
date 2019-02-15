package api.transfer;

import java.math.BigDecimal;

public interface TransferAction {

    Transfer execute(String senderAccountId, String recipientAccountId, BigDecimal amount);

    Transfer findById(String transferId);
}
