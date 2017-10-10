package lang.generics.inheritance;

public abstract  class Entity<T extends Id<? extends Entity>> {
    protected T id;
}
