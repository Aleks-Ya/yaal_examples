package usual;

import org.springframework.stereotype.Component;

@Component
public class PropertiesPojo {

    private String myNameFromProperty;

    private String bestCity;

    public String getMyNameFromProperty() {
        return myNameFromProperty;
    }

    public String getBestCity() {
        return bestCity;
    }
}
