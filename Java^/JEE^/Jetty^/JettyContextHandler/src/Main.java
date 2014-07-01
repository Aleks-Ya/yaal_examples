import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

public class Main {
    public static void main(String[] args) throws Exception {

        ContextHandler rootContext = new ContextHandler();// корневой контекст по-умолчанию (/)
        rootContext.setHandler(new ShowMessageHandler("Root context handler: /"));

        ContextHandler enContext = new ContextHandler();
        enContext.setContextPath("/en");
        enContext.setHandler(new ShowMessageHandler("English context handler: /en"));

        ContextHandler ruContext = new ContextHandler("/ru"); // можно задать путь к контексту в конструкторе
        ruContext.setHandler(new ShowMessageHandler("Обработчик русского контекста: /ru"));

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{rootContext, enContext, ruContext});

        Server server = new Server(8080);
        server.setHandler(contexts);
        server.start();
        server.join();
    }
}