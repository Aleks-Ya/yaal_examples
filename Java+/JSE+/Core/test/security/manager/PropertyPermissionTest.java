package security.manager;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static security.manager.SecurityManagerHelper.configureSecurityManagerPolicyFromResource;

@Disabled("need fix exception")
class PropertyPermissionTest {

    @Test
    void grantSingleProperty() {
        var propertyName = "the.single.secret.property";
        var expValue = "grantSingleProperty";
        System.setProperty(propertyName, expValue);

        configureSecurityManagerPolicyFromResource(getClass(), "PropertyPermissionTest_grantSingleProperty.policy");

        var actValue = System.getProperty(propertyName);
        assertThat(actValue).isEqualTo(expValue);
    }

    @Test
    void grantAllProperties() {
        var propertyName = "the.secret.property.2";
        var expValue = "grantAllProperties";
        System.setProperty(propertyName, expValue);

        configureSecurityManagerPolicyFromResource(getClass(), "PropertyPermissionTest_grantAllProperties.policy");

        var actValue = System.getProperty(propertyName);
        assertThat(actValue).isEqualTo(expValue);
    }

}
