import com.sun.jndi.fscontext.RefFSContext;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws NamingException {
        var props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.fscontext.RefFSContextFactory");

        Context initialContext = new InitialContext(props);

        {
            //Ищем в папке, заданной в свойстве Context.PROVIDER_URL
            var list = initialContext.listBindings("");
            while (list.hasMoreElements()) {
                NameClassPair classPair = list.next();
                System.out.printf("%20s - %s\n", classPair.getName(), classPair.getClassName());
            }
        }
        {
            //Ищем в папке, заданной во время поиска
            var tmpFolder = (RefFSContext) initialContext.lookup(System.getProperty("java.io.tmpdir"));
            var list = tmpFolder.listBindings("");
            while (list.hasMoreElements()) {
                var classPair = (NameClassPair) list.next();
                System.out.printf("%80s - %s\n", classPair.getName(), classPair.getClassName());
            }
        }
    }
}
