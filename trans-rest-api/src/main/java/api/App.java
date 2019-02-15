package api;

import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.get;

public final class App {

    private App() {
    }

    private static final Javalin app = create();

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        app.start();
    }

    public static void stop() {
        app.stop();
    }

    private static Javalin create() {
        return Javalin.create()
                .port(7070)
                .routes(() -> {
                    get("/health", ctx -> ctx.result("Running..."));
                });
    }
}
