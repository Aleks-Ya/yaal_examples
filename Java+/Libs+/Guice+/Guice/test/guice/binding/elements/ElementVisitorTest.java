package guice.binding.elements;

import com.google.inject.AbstractModule;
import com.google.inject.Binding;
import com.google.inject.spi.DefaultElementVisitor;
import com.google.inject.spi.Element;
import com.google.inject.spi.Elements;
import com.google.inject.spi.InstanceBinding;
import com.google.inject.spi.LinkedKeyBinding;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;
import java.io.Writer;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class ElementVisitorTest {
    private static final Integer INT = 7;

    @Test
    void visitBinding() {
        var module = new AppModule();
        var instances = Elements.getElements(module).stream()
                .map(element -> element.acceptVisitor(new BindingVisitor()))
                .collect(Collectors.toList());
        assertThat(instances).containsExactly(INT, "Key[type=java.io.StringWriter, annotation=[none]]");
    }

    @Test
    void visitOther() {
        var module = new AppModule();
        var instances = Elements.getElements(module).stream()
                .map(element -> element.acceptVisitor(new OtherVisitor()))
                .collect(Collectors.toList());
        assertThat(instances).containsExactly(INT, "Key[type=java.io.StringWriter, annotation=[none]]");
    }

    private static class BindingVisitor extends DefaultElementVisitor<Object> {
        @Override
        public Object visit(Binding binding) {
            if (binding instanceof InstanceBinding) {
                var instanceBinding = (InstanceBinding<Integer>) binding;
                return instanceBinding.getInstance();
            } else if (binding instanceof LinkedKeyBinding) {
                var linkedKeyBinding = (LinkedKeyBinding<Writer>) binding;
                return linkedKeyBinding.getLinkedKey().toString();
            }
            return null;
        }
    }

    private static class OtherVisitor extends DefaultElementVisitor<Object> {
        @Override
        protected Object visitOther(Element element) {
            if (element instanceof InstanceBinding) {
                var instanceBinding = (InstanceBinding<Integer>) element;
                return instanceBinding.getInstance();
            } else if (element instanceof LinkedKeyBinding) {
                var linkedKeyBinding = (LinkedKeyBinding<Writer>) element;
                return linkedKeyBinding.getLinkedKey().toString();
            }
            return null;
        }
    }

    private static class AppModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(Integer.class).toInstance(INT);
            bind(Writer.class).to(StringWriter.class);
        }
    }
}