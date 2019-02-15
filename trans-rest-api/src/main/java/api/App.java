package api;

import api.account.AccountAction;
import api.account.AccountRepository;
import api.account.AccountService;
import api.account.integration.AccountRestController;
import api.transfer.TransferRepository;
import api.transfer.TransferService;
import api.transfer.integration.TransferRestController;
import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.*;

public final class App {

    private final static AccountAction accountService = new AccountService(new AccountRepository());
    private final static AccountRestController accountRestController = new AccountRestController(accountService);
    private final static TransferRestController transferRestController = new TransferRestController(new TransferService(new TransferRepository(), accountService));

    private App() {
    }

    private static final Javalin APP = create();

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        APP.start();
    }

    public static void stop() {
        APP.stop();
    }

    private static Javalin create() {
        return new CustomJavalin()
                .port(7070)
                .routes(() -> {
                    get("/health", ctx -> ctx.result("Running..."));
                    path("/api", () -> {
                        get("/account/:account-id", ctx -> accountRestController.get(ctx));
                        post("/account", ctx -> accountRestController.post(ctx));
                        get("/transfer/:transfer-id", ctx -> transferRestController.get(ctx));
                        post("/transfer", ctx -> transferRestController.post(ctx));
                    });
                });
    }

    private static class CustomJavalin extends Javalin {
        @Override
        public Javalin stop() {
            super.stop();
            started = false;
            return this;
        }
    }
}
