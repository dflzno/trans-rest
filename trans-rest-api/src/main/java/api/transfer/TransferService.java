package api.transfer;

import api.ApiException;
import api.account.Account;
import api.account.AccountAction;
import api.account.AccountNotFoundException;
import api.dataaccess.Repository;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.Optional;

import static api.transfer.Transfer.TransferStatus.FAILED;
import static api.transfer.Transfer.TransferStatus.SUCCESSFUL;

public class TransferService implements TransferAction {

    private final Repository<Transfer> repository;
    private final AccountAction accountService;

    public TransferService(@NonNull final Repository<Transfer> repository, @NonNull final AccountAction accountService) {
        this.repository = repository;
        this.accountService = accountService;
    }

    @Override
    public TransferResult execute(final String senderAccountId, final String recipientAccountId, @NonNull final BigDecimal amount) {

        final Optional<Account> wantedSenderAccount = accountService.getById(senderAccountId);
        final Optional<Account> wantedRecipientAccount = accountService.getById(recipientAccountId);

        if (!wantedSenderAccount.isPresent() || !wantedRecipientAccount.isPresent()) {
            return saveTransfer(buildFailedTransferResult(senderAccountId, recipientAccountId, amount, new AccountNotFoundException()));
        }

        try {
            wantedSenderAccount.get().debit(amount);
        } catch (ApiException e) {
            return saveTransfer(buildFailedTransferResult(senderAccountId, recipientAccountId, amount, e));
        }
        wantedRecipientAccount.get().credit(amount);

        return saveTransfer(TransferResult.builder()
                .transfer(new Transfer(senderAccountId, recipientAccountId, amount, SUCCESSFUL))
                .build());
    }

    @Override
    public Optional<Transfer> findById(final String transferId) {
        return repository.getById(transferId);
    }

    private TransferResult buildFailedTransferResult(final String senderAccountId, final String recipientAccountId, final BigDecimal amount, final ApiException exception) {
        return TransferResult.builder()
                .transfer(new Transfer(senderAccountId, recipientAccountId, amount, FAILED))
                .exception(exception)
                .build();
    }

    private TransferResult saveTransfer(final TransferResult transferResult) {
        repository.save(transferResult.getTransfer());
        return transferResult;
    }
}
