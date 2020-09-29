package guice.binding.provider.package_access_provider.package_provider;

import com.google.inject.Provider;

/**
 * Provider has access to all interface implementations in its package.
 */
public class PackageProvider implements Provider<PackageService> {

    @Override
    public PackageService get() {
        return new PackageServiceImpl();
    }

}