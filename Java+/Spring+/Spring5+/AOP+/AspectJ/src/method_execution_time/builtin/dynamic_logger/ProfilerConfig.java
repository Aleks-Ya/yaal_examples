package method_execution_time.builtin.dynamic_logger;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Aspect
@Configuration
@EnableAspectJAutoProxy
class ProfilerConfig {
    @Bean
    Advisor performanceMonitorAdvisor(@Value("${pointcut}") String pointcutExpression) {
        var pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(pointcutExpression);
        var advice = new PerformanceMonitorInterceptor(true);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
