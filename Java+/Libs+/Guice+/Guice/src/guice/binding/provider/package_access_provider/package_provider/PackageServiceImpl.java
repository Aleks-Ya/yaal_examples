package guice.binding.provider.package_access_provider.package_provider;

class PackageServiceImpl implements PackageService {
    @Override
    public String getTitle() {
        return "the title";
    }
}
