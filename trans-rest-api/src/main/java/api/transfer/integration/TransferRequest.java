package api.transfer.integration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TransferRequest {

    private String senderAccountId;
    private String recipientAccountId;
    private BigDecimal amount;
}
