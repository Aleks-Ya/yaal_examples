package guice.binding.provider.provider_from_provider;

import guice.binding.provider.provider_from_provider.name.NameService;
import guice.binding.provider.provider_from_provider.title.TitleService;

class RootServiceImpl implements RootService {
    private final TitleService titleService;
    private final NameService nameService;

    public RootServiceImpl(TitleService titleService, NameService nameService) {
        this.titleService = titleService;
        this.nameService = nameService;
    }

    @Override
    public String getPerson() {
        return titleService.getTitle() + " " + nameService.getName();
    }
}
