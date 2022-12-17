package h2.save;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Config.class, City.class, CityRepository.class})
class SaveGeneratedIdTest {
    @Autowired
    private CityRepository repo;

    @Test
    void save() {
        var city = new City(null, "London");
        var citySaved = repo.save(city);
        assertThat(repo.findById(citySaved.getId())).contains(citySaved);
    }

    @Test
    void update() {
        var city = new City(null, "London");
        var citySaved = repo.save(city);
        var cityFind = repo.findById(citySaved.getId());
        assertThat(cityFind).contains(citySaved);
        var newName = "Paris";
        citySaved.setName(newName);
        var updated = repo.save(citySaved);
        var updated2 = repo.findById(citySaved.getId()).orElseThrow();
        assertThat(updated).isEqualTo(citySaved);
        assertThat(updated2).isEqualTo(citySaved);
    }

    @Test
    void saveWithId() {
        var city = new City(100L, "Berlin");
        var citySaved1 = repo.save(city);
        assertThat(city.getId()).isNotEqualTo(citySaved1.getId());
        citySaved1.setName("London");
        var citySaved2 = repo.save(citySaved1);
        assertThat(citySaved2.getId()).isEqualTo(citySaved1.getId());
        assertThat(repo.findById(citySaved2.getId())).contains(citySaved1);
    }

    @Test
    void saveWithId_newInstance() {
        var city = new City(100L, "Berlin");
        var citySaved1 = repo.save(city);
        assertThat(city.getId()).isNotEqualTo(citySaved1.getId());
        var city2 = new City(citySaved1.getId(), "London");
        var citySaved2 = repo.save(city2);
        assertThat(citySaved2.getId()).isEqualTo(citySaved1.getId());
        assertThat(repo.findById(citySaved2.getId())).contains(citySaved2);
    }
}
