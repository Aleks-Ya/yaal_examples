# UnpackDependency

Unpack a dependency that was deployed as a ZIP file.

## Unpack
By invoking the lifecycle: `mvn clean package`

By invoking the plugin:
```shell
mvn clean org.apache.maven.plugins:maven-dependency-plugin:3.8.1:unpack \
    -Dartifact=org.opensearch.plugin:neural-search:2.17.1.0:zip \
    -DoutputDirectory='${project.build.directory}/unpacked'
```

Output dir: `target/unpacked`