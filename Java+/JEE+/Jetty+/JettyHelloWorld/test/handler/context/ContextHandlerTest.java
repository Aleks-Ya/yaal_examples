package handler.context;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.junit.Test;
import util.Utils;

/**
 * Работа с Context в сервере Jetty.
 * Взят из документации http://www.eclipse.org/jetty/documentation/current/embedding-jetty.html#d0e18550
 */
public class ContextHandlerTest {

    @Test
    public void main() throws Exception {

        ContextHandler rootContext = new ContextHandler();// корневой контекст по-умолчанию (/)
        rootContext.setHandler(new ShowMessageHandler("Root context handler: /"));

        ContextHandler enContext = new ContextHandler();
        enContext.setContextPath("/en");
        enContext.setHandler(new ShowMessageHandler("English context handler: /en"));

        ContextHandler ruContext = new ContextHandler("/ru"); // можно задать путь к контексту в конструкторе
        ruContext.setHandler(new ShowMessageHandler("Обработчик русского контекста: /ru"));

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{rootContext, enContext, ruContext});

        int port = 8080;
        Server server = new Server(port);
        server.setHandler(contexts);
        server.start();

        Utils.assertUrlContent("http://localhost:" + port + "/en", "<h1>English context handler: /en</h1>");
        Utils.assertUrlContent("http://localhost:" + port + "/ru", "<h1>Обработчик русского контекста: /ru</h1>");

        server.stop();
    }
}