package api.dataaccess;

import java.util.Optional;

public interface Repository<T> {

    Optional<T> getById(String id);

    void save(T object);
}
