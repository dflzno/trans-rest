package api.transfer.integration;

import api.transfer.Transfer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TransferResultResponse {
    private Transfer transfer;
    private String error;

    public TransferResultResponse(final Transfer transfer) {
        this.transfer = transfer;
    }
}
