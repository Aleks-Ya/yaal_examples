package method_execution_time.builtin.static_logger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import util.InputStreamUtil;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ProfilerConfig.class, PersonService.class})
@TestPropertySource(properties = "pointcut=execution(* method_execution_time.builtin.static_logger.PersonService.*(..))")
class PerformanceMonitorInterceptorStaticLoggerTest {
    private static final String staticLoggerName = PerformanceMonitorInterceptor.class.getName();

    static {
        System.setProperty("org.slf4j.simpleLogger.log." + staticLoggerName, "TRACE");
    }

    @Autowired
    private PersonService personService;

    @Test
    void test() throws InterruptedException {
        var out = InputStreamUtil.redirectStdErr();
        assertThat(LoggerFactory.getLogger(staticLoggerName).isTraceEnabled()).isTrue();
        var person = new Person("John", "Mark");
        var fullName = personService.getFullName(person);
        assertThat(fullName).isEqualTo("John Mark");
        assertThat(out.toString())
                .contains("[Test worker] TRACE org.springframework.aop.interceptor.PerformanceMonitorInterceptor - " +
                        "StopWatch 'method_execution_time.builtin.static_logger.PersonService.getFullName': running time =");
    }
}
