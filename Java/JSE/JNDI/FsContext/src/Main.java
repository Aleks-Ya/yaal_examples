import com.sun.jndi.fscontext.RefFSContext;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws NamingException {
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.fscontext.RefFSContextFactory");

        Context initialContext = new InitialContext(props);

        {
            //Ищем в папке, заданной в свойстве Context.PROVIDER_URL
            NamingEnumeration<Binding> list = initialContext.listBindings("");
            while (list.hasMoreElements()) {
                NameClassPair classPair = list.next();
                System.out.printf("%20s - %s\n", classPair.getName(), classPair.getClassName());
            }
        }
        {
            //Ищем в папке, заданной во время поиска
            RefFSContext tmpFolder = (RefFSContext) initialContext.lookup(System.getProperty("java.io.tmpdir"));
            NamingEnumeration list = tmpFolder.listBindings("");
            while (list.hasMoreElements()) {
                NameClassPair classPair = (NameClassPair) list.next();
                System.out.printf("%80s - %s\n", classPair.getName(), classPair.getClassName());
            }
        }
    }
}
