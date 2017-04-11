package ru.yaal.merch.bookshelf;

import ru.yaal.merch.bookshelf.config.Profiles;

public class ApplicationDemo {
    public static void main(String[] args) throws Exception {
        ApplicationProd.runApp(Profiles.DEMO);
    }
}
