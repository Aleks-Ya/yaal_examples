# gradle CLI

## Info
Help: `gradle -h`
Log level:
- `gradle --warn`, `gradle -w`
- `gradle --info`, `gradle -i`
- `gradle --debug`, `gradle -d`

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
Set property: `gradle -Pabc=123 properties`
List available tasks:
```
gradle tasks
gradle tasks --all
```
Specify project dir: `gradle -p /my/project/dir build`
