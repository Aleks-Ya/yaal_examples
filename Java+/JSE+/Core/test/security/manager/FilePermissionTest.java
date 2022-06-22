package security.manager;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.security.AccessControlException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static security.manager.SecurityManagerHelper.configureSecurityManagerPolicyFromResource;

@Disabled("need fix exception")
class FilePermissionTest {

    @Test
    void grantSingleFile() {
        configureSecurityManagerPolicyFromResource(getClass(), "PropertyPermissionTest_grantSingleFile.policy");

        var grantedFile = new File("granted.txt");
        assertThat(grantedFile.exists()).isEqualTo(false);

        var notGrantedFile = new File("not_granted.txt");
        //noinspection ResultOfMethodCallIgnored
        assertThatThrownBy(notGrantedFile::exists).isInstanceOf(AccessControlException.class);
    }

    @Test
    void grantAllFiles() {
        configureSecurityManagerPolicyFromResource(getClass(), "PropertyPermissionTest_grantAllFiles.policy");

        var file = new File("abc.txt");
        var exists = file.exists();
        assertThat(exists).isEqualTo(false);
    }

}
