package api.dataaccess.db;

import api.transfer.Transfer;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class TransfersTest {

    @Test
    public void shouldSaveAndRetrieveATransfer() {
        // given
        Transfer t = new Transfer("acc1", "acc2", BigDecimal.TEN);

        // when
        Transfers.save(t);

        // then
        assertThat(Transfers.getById(t.getTransferId())).isPresent();
        Transfer retrievedTransfer = Transfers.getById(t.getTransferId()).get();
        assertThat(retrievedTransfer.getSenderAccountId()).isEqualTo("acc1");
        assertThat(retrievedTransfer.getRecipientAccountId()).isEqualTo("acc2");
        assertThat(retrievedTransfer.getAmount()).isEqualTo(BigDecimal.TEN);
    }

}