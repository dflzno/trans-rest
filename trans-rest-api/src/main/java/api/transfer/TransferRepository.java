package api.transfer;

import api.dataaccess.Repository;
import api.dataaccess.db.Transfers;

import java.util.Optional;

public class TransferRepository implements Repository<Transfer> {

    @Override
    public Optional<Transfer> getById(String id) {
        return Transfers.getById(id);
    }

    @Override
    public void save(Transfer object) {
        Transfers.save(object);
    }
}
