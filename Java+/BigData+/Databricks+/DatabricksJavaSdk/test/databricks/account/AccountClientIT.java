package databricks.account;

import com.databricks.sdk.AccountClient;
import com.databricks.sdk.service.iam.AccountUsersAPI;
import com.databricks.sdk.service.iam.ListAccountUsersRequest;
import org.junit.jupiter.api.Test;

class AccountClientIT {
    private final AccountClient a = new AccountClient();
    private final AccountUsersAPI users = a.users();

    @Test
    void listUsers() {
        var list = users.list(new ListAccountUsersRequest());
        System.out.println(list);
    }
}