package jpa.metamodel;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
class Mayor {
    private Long mayorId;
    private String mayorName;

    public Mayor() {
    }

    public Mayor(Long mayorId, String mayorName) {
        this.mayorId = mayorId;
        this.mayorName = mayorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mayor mayor = (Mayor) o;
        return Objects.equals(mayorId, mayor.mayorId) && Objects.equals(mayorName, mayor.mayorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mayorId, mayorName);
    }
}
