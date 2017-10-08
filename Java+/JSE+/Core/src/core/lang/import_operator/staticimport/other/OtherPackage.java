package core.lang.import_operator.staticimport.other;

/**
 * Для демонстрации статического импорта (конфликт имени поля и метода).
 */
public class OtherPackage {
    public static int number = 1;

    public static int number(){
        return 2;
    }
}
