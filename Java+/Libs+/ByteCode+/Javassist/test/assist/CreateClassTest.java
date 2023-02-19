package assist;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.bytecode.AccessFlag;
import javassist.bytecode.ClassFile;
import javassist.bytecode.FieldInfo;
import org.junit.jupiter.api.Test;

import java.lang.invoke.MethodHandles;

import static org.assertj.core.api.Assertions.assertThat;

class CreateClassTest {
    @Test
    void create() throws CannotCompileException {
        var className = getClass().getPackageName() + ".JavassistGeneratedClass";
        var cf = new ClassFile(false, className, null);
        cf.setInterfaces(new String[]{"java.lang.Cloneable"});

        var f = new FieldInfo(cf.getConstPool(), "id", "I");
        f.setAccessFlags(AccessFlag.PUBLIC);
        cf.addField(f);

        var classPool = ClassPool.getDefault();
        var fields = classPool.makeClass(cf).toClass(MethodHandles.lookup()).getFields();

        assertThat(fields[0].getName()).isEqualTo("id");
    }
}