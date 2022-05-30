# Run Sonar scanner


```
mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent package sonar:sonar \
    -Dsonar.host.url=https://sonarcloud.io \
    -Dsonar.organization=aleks-ya-github \
    -Dsonar.login=30d308f1d9105bc0a5c55c42c375790c504f0a68
```
