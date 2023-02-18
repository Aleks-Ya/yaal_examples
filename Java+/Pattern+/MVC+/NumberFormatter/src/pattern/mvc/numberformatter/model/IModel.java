package pattern.mvc.numberformatter.model;

/**
 * Интерфейс моделей.
 */
public interface IModel {
    void eventNumberChanged(double number);

    void eventError(String message);

    void addChangedListener(IModelListener listener);
}
