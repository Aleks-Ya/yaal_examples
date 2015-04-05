package xml;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration("classpath:xml/context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class XmlCallback {

    /**
     * Пустой тест заставляет Spring инъецировать бины.
     */
    @Test
    public void test() throws InterruptedException {
        Thread.sleep(300);
        System.out.println("Метод уничтожения бина вызываются не каждый раз...");
    }
}