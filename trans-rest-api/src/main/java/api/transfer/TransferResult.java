package api.transfer;

import api.ApiException;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@Builder
@Getter
public class TransferResult {
    @NonNull
    private final Transfer transfer;
    private ApiException error;

    public Optional<ApiException> getError() {
        return ofNullable(this.error);
    }
}
