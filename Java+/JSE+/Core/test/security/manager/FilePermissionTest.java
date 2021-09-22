package security.manager;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.security.AccessControlException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static security.manager.SecurityManagerHelper.configureSecurityManagerPolicyFromResource;

@Disabled("need fix exception")
class FilePermissionTest {

    @Test
    void grantSingleFile() {
        configureSecurityManagerPolicyFromResource(getClass(), "PropertyPermissionTest_grantSingleFile.policy");

        var grantedFile = new File("granted.txt");
        assertThat(grantedFile.exists(), equalTo(false));

        var notGrantedFile = new File("not_granted.txt");
        //noinspection ResultOfMethodCallIgnored
        assertThrows(AccessControlException.class, notGrantedFile::exists);
    }

    @Test
    void grantAllFiles() {
        configureSecurityManagerPolicyFromResource(getClass(), "PropertyPermissionTest_grantAllFiles.policy");

        var file = new File("abc.txt");
        var exists = file.exists();
        assertThat(exists, equalTo(false));
    }

}
