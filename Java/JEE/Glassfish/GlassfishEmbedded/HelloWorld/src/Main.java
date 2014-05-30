import org.glassfish.embeddable.GlassFish;
import org.glassfish.embeddable.GlassFishException;
import org.glassfish.embeddable.GlassFishProperties;
import org.glassfish.embeddable.GlassFishRuntime;

public class Main {
    public static void main(String[] args) throws GlassFishException {
        GlassFishProperties gfProps = new GlassFishProperties();
        gfProps.setPort("http-listener", 8080);

        GlassFish glassfish = GlassFishRuntime.bootstrap().newGlassFish();
        glassfish.start();
    }
}
