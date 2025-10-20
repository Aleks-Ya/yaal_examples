# Resolve dependency from a Maven repo in a local Artifactory

1. Publish an artifact to Artifactory: `Building+/Ant+/Ivy+/PublishToArtifactoryMaven`
2. Just resolve the dependency:
    ```shell
    java -jar \
        -Dartifactory.user=admin \
        -Dartifactory.pass=_Password111 \
        $ANT_HOME/lib/ivy.jar \
        -settings ivysettings.xml \
        -dependency ru.yaal.ant PublishToArtifactoryMaven 1.0.0
    ```
3. Resolve and build: `ant -Dartifactory.user=admin -Dartifactory.pass=_Password111`
