package guice.binding.provider.provider_from_provider.name;

class NameServiceImpl implements NameService {
    private final String name;

    public NameServiceImpl(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
