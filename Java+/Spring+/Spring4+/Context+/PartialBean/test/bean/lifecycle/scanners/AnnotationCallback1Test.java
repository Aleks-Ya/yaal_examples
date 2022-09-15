package bean.lifecycle.scanners;

import bean.lifecycle.MyBean;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = {AnnotationBean.class, MyBean.class})
@RunWith(SpringJUnit4ClassRunner.class)
class AnnotationCallback1Test {

    /**
     * Пустой тест заставляет Spring инъецировать бины.
     */
    @Test
    void test() throws InterruptedException {
        Thread.sleep(300);
        System.out.println("Метод уничтожения бина вызываются не каждый раз...");
    }
}