package map.guide;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CarMapperTest {
    @Test
    public void shouldMapCarToDto() {
        var car = new Car("Morris", 5, CarType.BIG);
        var carDto = CarMapper.INSTANCE.carToCarDto(car);
        assertThat(carDto).isNotNull();
        assertThat(carDto.getMake()).isEqualTo("Morris");
        assertThat(carDto.getSeatCount()).isEqualTo(5);
        assertThat(carDto.getType()).isEqualTo("BIG");
    }
}