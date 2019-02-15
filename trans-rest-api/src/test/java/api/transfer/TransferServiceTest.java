package api.transfer;

import api.account.Account;
import api.account.AccountNotFoundException;
import api.account.AccountService;
import api.account.NotEnoughFundsException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static api.transfer.Transfer.TransferStatus.FAILED;
import static api.transfer.Transfer.TransferStatus.SUCCESSFUL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransferServiceTest {

    @Mock
    private AccountService accountService;

    private TransferService testSubject;

    @Before
    public void setUp() {
        when(accountService.getById("sender")).thenReturn(Optional.of(new Account(new BigDecimal("2500"))));
        when(accountService.getById("recipient")).thenReturn(Optional.of(new Account(new BigDecimal("120"))));

        testSubject = new TransferService(new TransferRepository(), accountService);
    }

    @Test
    public void shouldFindTheGivenTransfer() {
        // given
        final TransferRepository transferRepository = mock(TransferRepository.class);
        when(transferRepository.getById("transferId")).thenReturn(Optional.of(new Transfer("sender", "recipient", BigDecimal.ZERO, SUCCESSFUL)));
        testSubject = new TransferService(transferRepository, accountService);

        // when
        final Optional<Transfer> transfer = testSubject.findById("transferId");

        // then
        assertThat(transfer).isPresent();
    }

    @Test
    public void shouldSuccessfullyTransferTheMoneyFromSenderToRecipient() {
        // when
        final TransferResult result = testSubject.execute("sender", "recipient", new BigDecimal("350"));

        // then
        assertThat(result.getException()).isEmpty();
        assertThat(result.getTransfer().getStatus()).isEqualByComparingTo(SUCCESSFUL);
        assertBasicTransferAttributes(result.getTransfer());
    }

    @Test
    public void shouldIndicateAFailedTransactionWhenTheSenderAccountDoesNotExist() {
        // when
        final TransferResult result = testSubject.execute("fakeSender", "recipient", new BigDecimal("350"));

        // then
        assertThat(result.getException()).isPresent();
        assertThat(result.getException().get()).isInstanceOf(AccountNotFoundException.class);
        assertThat(result.getTransfer().getStatus()).isEqualByComparingTo(FAILED);
        assertBasicTransferAttributes(result.getTransfer());
    }

    @Test
    public void shouldIndicateAFailedTransactionWhenTheRecipientAccountDoesNotExist() {
        // when
        final TransferResult result = testSubject.execute("sender", "fakeRecipient", new BigDecimal("350"));

        // then
        assertThat(result.getException()).isPresent();
        assertThat(result.getException().get()).isInstanceOf(AccountNotFoundException.class);
        assertThat(result.getTransfer().getStatus()).isEqualByComparingTo(FAILED);
        assertBasicTransferAttributes(result.getTransfer());
    }

    @Test
    public void shouldIndicateAFailedTransactionWhenItIsNotPossibleToDebitFromTheSender() {
        // when
        final TransferResult result = testSubject.execute("sender", "recipient", new BigDecimal("4000"));

        // then
        assertThat(result.getException()).isPresent();
        assertThat(result.getException().get()).isInstanceOf(NotEnoughFundsException.class);
        assertThat(result.getTransfer().getStatus()).isEqualByComparingTo(FAILED);
        assertBasicTransferAttributes(result.getTransfer());
    }

    private static void assertBasicTransferAttributes(final Transfer transfer) {
        assertThat(transfer.getSenderAccountId()).isNotBlank();
        assertThat(transfer.getRecipientAccountId()).isNotBlank();
        assertThat(transfer.getTransferId()).isNotBlank();
    }

}