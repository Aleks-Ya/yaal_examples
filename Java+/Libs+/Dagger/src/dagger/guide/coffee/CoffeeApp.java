package dagger.guide.coffee;

import dagger.Component;

import javax.inject.Singleton;

/**
 * Source: https://github.com/google/dagger/tree/master/examples/simple/src/main/java/coffee
 */
public class CoffeeApp {
  @Singleton
  @Component(modules = { DripCoffeeModule.class })
  public interface CoffeeShop {
    CoffeeMaker maker();
  }

  public static void main(String[] args) {
    CoffeeShop coffeeShop = DaggerCoffeeApp_CoffeeShop.builder().build();
    coffeeShop.maker().brew();
  }
}
