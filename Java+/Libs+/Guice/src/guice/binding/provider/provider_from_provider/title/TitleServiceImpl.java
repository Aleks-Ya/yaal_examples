package guice.binding.provider.provider_from_provider.title;

class TitleServiceImpl implements TitleService {
    private final String title;

    public TitleServiceImpl(String title) {
        this.title = title;
    }

    @Override
    public String getTitle() {
        return title;
    }
}
