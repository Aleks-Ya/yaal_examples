package annotation.mayor;

public class MayorAssistant {
    private String fio;

    public MayorAssistant(String fio) {
        this.fio = fio;
    }

    @Override
    public String toString() {
        return fio;
    }
}
