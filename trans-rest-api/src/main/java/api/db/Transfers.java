package api.db;

import api.transfer.Transfer;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.ofNullable;

public final class Transfers {

    private static final Map<String, Transfer> transfers = new HashMap<>();

    private Transfers() {
    }

    public static void save(@NonNull final Transfer transfer) {
        transfers.put(transfer.getTransferId(), transfer);
    }

    public static Optional<Transfer> getById(@NonNull final String transferId) {
        return ofNullable(transfers.get(transferId));
    }

}
