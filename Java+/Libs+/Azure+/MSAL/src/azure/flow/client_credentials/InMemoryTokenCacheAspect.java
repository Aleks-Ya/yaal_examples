package azure.flow.client_credentials;

import com.microsoft.aad.msal4j.ITokenCacheAccessAspect;
import com.microsoft.aad.msal4j.ITokenCacheAccessContext;

class InMemoryTokenCacheAspect implements ITokenCacheAccessAspect {
    private String cache;

    @Override
    public void beforeCacheAccess(ITokenCacheAccessContext iTokenCacheAccessContext) {
        iTokenCacheAccessContext.tokenCache().deserialize(cache);
    }

    @Override
    public void afterCacheAccess(ITokenCacheAccessContext iTokenCacheAccessContext) {
        cache = iTokenCacheAccessContext.tokenCache().serialize();
    }
}
