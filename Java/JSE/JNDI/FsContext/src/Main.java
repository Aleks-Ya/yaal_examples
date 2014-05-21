import com.sun.jndi.fscontext.RefFSContext;

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
        props.put(Context.PROVIDER_URL, "file:///");

        Context initialContext = new InitialContext(props);

        {
            //Ищем в папке, заданной в свойстве Context.PROVIDER_URL
            RefFSContext tmpFolder = (RefFSContext) initialContext.lookup("");
            NamingEnumeration<Object> list = tmpFolder.list("");
            while (list.hasMoreElements()) {
                NameClassPair classPair = (NameClassPair) list.next();
                System.out.println(classPair.getName());
            }
        }
        {
            //Ищем в папке, заданной во вермя поиска
            RefFSContext tmpFolder = (RefFSContext) initialContext.lookup(System.getProperty("java.io.tmpdir"));
            NamingEnumeration<Object> list = tmpFolder.list("");
            while (list.hasMoreElements()) {
                NameClassPair classPair = (NameClassPair) list.next();
                System.out.println(classPair.getName());
            }
        }
    }
}
