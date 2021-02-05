package azure.bank;

import com.microsoft.aad.msal4j.IAccount;
import com.microsoft.aad.msal4j.IAuthenticationResult;
import com.microsoft.aad.msal4j.MsalException;
import com.microsoft.aad.msal4j.PublicClientApplication;
import com.microsoft.aad.msal4j.SilentParameters;
import com.microsoft.aad.msal4j.UserNamePasswordParameters;

import java.util.Collections;
import java.util.Set;

/**
 * Source: https://github.com/Azure-Samples/ms-identity-java-desktop/blob/master/Username-Password-Flow/src/main/java/UsernamePasswordFlow.java
 */
public class BankApiApp {
    private static final String authority = System.getProperty("authority");
    private static final Set<String> scope = Collections.singleton(System.getProperty("scope"));
    private static final String clientId = System.getProperty("client_id");
    private static final String username = System.getProperty("user_name");
    private static final String password = System.getProperty("user_password");

    public void start() throws Exception {

        PublicClientApplication pca = PublicClientApplication.builder(clientId)
                .authority(authority)
                .build();

        //Get list of accounts from the application's token cache, and search them for the configured username
        //getAccounts() will be empty on this first call, as accounts are added to the cache when acquiring a token
        Set<IAccount> accountsInCache = pca.getAccounts().join();
        IAccount account = getAccountByUsername(accountsInCache, username);

        //Attempt to acquire token when user's account is not in the application's token cache
        IAuthenticationResult result = acquireTokenUsernamePassword(pca, scope, account, username, password);
        System.out.println("Account username: " + result.account().username());
        System.out.println("Access token:     " + result.accessToken());
        System.out.println("Id token:         " + result.idToken());
        System.out.println();

        accountsInCache = pca.getAccounts().join();
        account = getAccountByUsername(accountsInCache, username);

        //Attempt to acquire token again, now that the user's account and a token are in the application's token cache
        result = acquireTokenUsernamePassword(pca, scope, account, username, password);
        System.out.println("Account username: " + result.account().username());
        System.out.println("Access token:     " + result.accessToken());
        System.out.println("Id token:         " + result.idToken());
    }

    private static IAuthenticationResult acquireTokenUsernamePassword(PublicClientApplication pca,
                                                                      Set<String> scope,
                                                                      IAccount account,
                                                                      String username,
                                                                      String password) throws Exception {
        IAuthenticationResult result;
        try {
            SilentParameters silentParameters =
                    SilentParameters
                            .builder(scope)
                            .account(account)
                            .build();
            // Try to acquire token silently. This will fail on the first acquireTokenUsernamePassword() call
            // because the token cache does not have any data for the user you are trying to acquire a token for
            result = pca.acquireTokenSilently(silentParameters).join();
            System.out.println("==acquireTokenSilently call succeeded");
        } catch (Exception ex) {
            if (ex.getCause() instanceof MsalException) {
                System.out.println("==acquireTokenSilently call failed: " + ex.getCause());
                UserNamePasswordParameters parameters =
                        UserNamePasswordParameters
                                .builder(scope, username, password.toCharArray())
                                .build();
                // Try to acquire a token via username/password. If successful, you should see
                // the token and account information printed out to console
                result = pca.acquireToken(parameters).join();
                System.out.println("==username/password flow succeeded");
            } else {
                // Handle other exceptions accordingly
                throw ex;
            }
        }
        return result;
    }

    /**
     * Helper function to return an account from a given set of accounts based on the given username,
     * or return null if no accounts in the set match
     */
    private static IAccount getAccountByUsername(Set<IAccount> accounts, String username) {
        if (accounts.isEmpty()) {
            System.out.println("==No accounts in cache");
        } else {
            System.out.println("==Accounts in cache: " + accounts.size());
            for (IAccount account : accounts) {
                if (account.username().equals(username)) {
                    return account;
                }
            }
        }
        return null;
    }
}
