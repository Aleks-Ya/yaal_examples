# gradle CLI

Compile sources and tests:
```
gradle clean compileJava
gradle clean compileTestJava
```
Show dependency report (print dependency tree):
```
#In current project (without sub-projects)
gradle dependencies

#In specific project
gradle my_project:my_subproject:dependencies

#In specific configuration
gradle dependencies --configuration implementation
gradle dependencies --configuration testImplementation
```
Show dependency insight report: `gradle -q dependencyInsight --dependency commons-codec --configuration scm`
Publish artifact to Maven local repo: `gradle publishToMavenLocal`
Print properties: `gradle properties`
List available tasks:
```
gradle tasks
gradle tasks --all
```