package security.manager;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static security.manager.SecurityManagerHelper.configureSecurityManagerPolicyFromResource;

public class PropertyPermissionTest {

    @Test
    public void grantSingleProperty() {
        var propertyName = "the.single.secret.property";
        var expValue = "grantSingleProperty";
        System.setProperty(propertyName, expValue);

        configureSecurityManagerPolicyFromResource(getClass(), "PropertyPermissionTest_grantSingleProperty.policy");

        var actValue = System.getProperty(propertyName);
        assertThat(actValue, equalTo(expValue));
    }

    @Test
    public void grantAllProperties() {
        var propertyName = "the.secret.property.2";
        var expValue = "grantAllProperties";
        System.setProperty(propertyName, expValue);

        configureSecurityManagerPolicyFromResource(getClass(), "PropertyPermissionTest_grantAllProperties.policy");

        var actValue = System.getProperty(propertyName);
        assertThat(actValue, equalTo(expValue));
    }

}
