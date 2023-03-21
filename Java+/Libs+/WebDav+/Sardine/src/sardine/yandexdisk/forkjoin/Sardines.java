package sardine.yandexdisk.forkjoin;

import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;

class Sardines {
    private static final Settings settings = Settings.getSettings();
    private static final ThreadLocal<Sardine> sardineThreadLocal = ThreadLocal.withInitial(() ->
            SardineFactory.begin(settings.username(), settings.password()));

    public static Sardine get() {
        return sardineThreadLocal.get();
    }
}
