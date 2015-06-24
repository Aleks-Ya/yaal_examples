package generics.inheritance;

/**
 * @author yablokov a.
 */
public abstract  class Entity<T extends Id> {
    protected T id;
}
