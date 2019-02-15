package api.transfer;

import java.math.BigDecimal;
import java.util.Optional;

public interface TransferAction {

    TransferResult execute(String senderAccountId, String recipientAccountId, BigDecimal amount);

    Optional<Transfer> findById(String transferId);
}
