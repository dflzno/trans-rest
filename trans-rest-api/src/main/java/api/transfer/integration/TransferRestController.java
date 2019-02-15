package api.transfer.integration;

import api.transfer.Transfer;
import api.transfer.TransferAction;
import api.transfer.TransferResult;
import io.javalin.Context;

import java.util.Optional;

public class TransferRestController {

    private TransferAction transferService;

    public TransferRestController(final TransferAction transferService) {
        this.transferService = transferService;
    }

    public void get(final Context context) {
        Optional<Transfer> wantedTransfer = transferService.findById(context.pathParam("transfer-id"));
        if (wantedTransfer.isPresent()) {
            context.status(200).json(wantedTransfer.get());
        } else {
            context.status(404);
        }
    }

    public void post(final Context context) {
        final TransferRequest request = context.bodyAsClass(TransferRequest.class);

        final TransferResult result = transferService.execute(request.getSenderAccountId(), request.getRecipientAccountId(), request.getAmount());

        if (result == null) {
            context.status(500);
            return;
        }

        if (result.getError().isPresent()) {
            context.status(400).json(new TransferResultResponse(result.getTransfer(), result.getError().get().getMessage()));
        } else {
            context.status(201).json(new TransferResultResponse(result.getTransfer()));
        }
    }
}
