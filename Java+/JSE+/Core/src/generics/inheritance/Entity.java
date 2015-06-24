package generics.inheritance;

/**
 * @author yablokov a.
 */
public abstract  class Entity<T extends Id<? extends Entity>> {
    protected T id;
}
