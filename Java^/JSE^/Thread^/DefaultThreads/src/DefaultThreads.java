import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;

/**
 * Выводит имена потоков JVM, созданных по-умолчанию.
 * Компиляция: {@code javac DefautlThreads.java}
 * Запуск: {@code java DefaultThreads}
 */
class DefaultThreads {
    public static void main(String[] args) throws InterruptedException {
        while (true) {
            printThreads("by Thread.getAllStackTraces()", threadsByStackTraces());
            printThreads("by thread groups", threadsByThreadGroups());
            printThreadNamesByThreadMXBean();
            Thread.sleep(5000);
        }
    }

    private static Thread[] threadsByStackTraces() {
        return Thread.getAllStackTraces().keySet().toArray(new Thread[1]);
    }

    private static Thread[] threadsByThreadGroups() {
        //Определяем самую верхнюю группу потоков
        ThreadGroup rootGroup = Thread.currentThread().getThreadGroup();
        ThreadGroup parentGroup;
        while ((parentGroup = rootGroup.getParent()) != null) {
            rootGroup = parentGroup;
        }
        //Получаем потоки из верхней группы (рекурсивно)
        Thread[] threads = new Thread[rootGroup.activeCount()];
        while (rootGroup.enumerate(threads, true) == threads.length) {
            threads = new Thread[threads.length * 2];
        }
        //Исключаем пустые ячейки массива
        List<Thread> threadList = new ArrayList<>();
        for (Thread thread : threads) {
            if (thread != null) {
                threadList.add(thread);
            }
        }
        //Возвращаем результат
        return threadList.toArray(new Thread[1]);
    }

    private static void printThreadNamesByThreadMXBean() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        long[] ids = bean.getAllThreadIds();
        System.out.printf("Thread's by ThreadMXBean: %d\n", ids.length);
        ThreadInfo[] infos = bean.getThreadInfo(ids);
        for (ThreadInfo info : infos) {
            System.out.println(info.getThreadName());
        }
    }

    /**
     * Печатет имена потоков в консоль.
     */
    private static void printThreads(String comment, Thread[] threads) {
        System.out.println(comment);
        System.out.printf("Thread count is %d", threads.length);
        for (Thread thread : threads) {
            System.out.println(thread.getName());
        }
        System.out.println("--------------------------");
    }
}