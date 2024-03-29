package bean.lifecycle.scanners;

import bean.lifecycle.MyBean;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        AnnotationBean.class,
        MyBean.class
})
class AnnotationCallbackTest {

    /**
     * Пустой тест заставляет Spring инъецировать бины.
     */
    @Test
    void test() throws InterruptedException {
        Thread.sleep(300);
        System.out.println("Метод уничтожения бина вызываются не каждый раз...");
    }
}