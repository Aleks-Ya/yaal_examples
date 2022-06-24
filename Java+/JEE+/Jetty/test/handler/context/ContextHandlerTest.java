package handler.context;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NetworkConnector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.junit.jupiter.api.Test;

import static util.NetAsserts.assertUrlContent;

/**
 * Работа с Context в сервере Jetty.
 * Взят из документации http://www.eclipse.org/jetty/documentation/current/embedding-jetty.html#d0e18550
 */
public class ContextHandlerTest {

    @Test
    void main() throws Exception {
        var rootContext = new ContextHandler();// корневой контекст по-умолчанию (/)
        rootContext.setHandler(new ShowMessageHandler("Root context handler: /"));

        var enContext = new ContextHandler();
        enContext.setContextPath("/en");
        enContext.setHandler(new ShowMessageHandler("English context handler: /en"));

        var ruContext = new ContextHandler("/ru"); // можно задать путь к контексту в конструкторе
        ruContext.setHandler(new ShowMessageHandler("Обработчик русского контекста: /ru"));

        var contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{rootContext, enContext, ruContext});

        var server = new Server(0);
        server.setHandler(contexts);
        server.start();
        var port = ((NetworkConnector) server.getConnectors()[0]).getLocalPort();

        assertUrlContent("http://localhost:" + port + "/en", "<h1>English context handler: /en</h1>");
        assertUrlContent("http://localhost:" + port + "/ru", "<h1>Обработчик русского контекста: /ru</h1>");

        server.stop();
    }
}