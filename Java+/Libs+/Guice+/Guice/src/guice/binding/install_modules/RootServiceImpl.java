package guice.binding.install_modules;


import guice.binding.install_modules.name.NameService;
import guice.binding.install_modules.title.TitleService;

import jakarta.inject.Inject;

class RootServiceImpl implements RootService {
    private final TitleService titleService;
    private final NameService nameService;

    @Inject
    public RootServiceImpl(TitleService titleService, NameService nameService) {
        this.titleService = titleService;
        this.nameService = nameService;
    }

    @Override
    public String getPerson() {
        return titleService.getTitle() + " " + nameService.getName();
    }
}
