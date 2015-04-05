package scan.person;

public abstract class AbstractPerson implements IPerson {
    @Override
    public String toString() {
        return getFio();
    }
}
