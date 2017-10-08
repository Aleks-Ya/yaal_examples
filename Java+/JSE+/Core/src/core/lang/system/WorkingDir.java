package core.lang.system;

/**
 * Получение рабочей папки, в которой запущено данное приложение.
 */
public class WorkingDir {
	
	//TODO Работает ли в Windows??
    public static void main(String[] args) {
        //Linux:
        System.out.printf("System.getenv(\"PWD\")           -> %s%n", System.getenv("PWD"));
        //При запуске из Idea PWD заполняется неправильно
        
		System.out.printf("System.getProperty(\"user.dir\") -> %s%n", System.getProperty("user.dir"));
    }
}