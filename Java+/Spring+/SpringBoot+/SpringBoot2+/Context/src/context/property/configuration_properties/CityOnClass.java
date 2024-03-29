package context.property.configuration_properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;

@ConfigurationProperties("information.city-info")
class CityOnClass {
    private String title;
    private int year;
    private URI uri;
    private URI previousUri;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public URI getPreviousUri() {
        return previousUri;
    }

    public void setPreviousUri(URI previousUri) {
        this.previousUri = previousUri;
    }

    @Override
    public String toString() {
        return title + "-" + year + " at " + uri + " or at " + previousUri;
    }
}
