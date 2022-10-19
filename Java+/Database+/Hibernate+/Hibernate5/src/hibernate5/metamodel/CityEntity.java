package hibernate5.metamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static hibernate5.metamodel.CityEntity.TABLE_NAME;

@Entity
@Table(name = TABLE_NAME)
class CityEntity {
    public static final String TABLE_NAME = "city_physical";
    public static final String COLUMN_NAME = "city_name_1";
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = COLUMN_NAME)
    private String cityName;

    private Long cityPopulation;

    public CityEntity() {
    }

    public CityEntity(String cityName, Long cityPopulation) {
        this.cityName = cityName;
        this.cityPopulation = cityPopulation;
    }

    @Override
    public String toString() {
        return String.format("City[id=%d, cityName=%s, cityPopulation=%d]", id, cityName, cityPopulation);
    }
}