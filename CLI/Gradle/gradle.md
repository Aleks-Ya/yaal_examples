# gradle CLI

## Info
Help: `gradle -h`
Version: `gradle -v`
Log level:
- `gradle --warn`, `gradle -w`
- `gradle --info`, `gradle -i`
- `gradle --debug`, `gradle -d`

## Compile
Compile sources and tests:
```shell
gradle clean compileJava
gradle clean compileTestJava
```

## Dependencies
Show dependency report (print dependency tree):
```shell
#In current project (without sub-projects)
gradle dependencies

#In specific project
gradle my_project:my_subproject:dependencies

#In specific configuration
gradle dependencies --configuration implementation
gradle dependencies --configuration testImplementation
```
Show dependency insight report: `gradle -q dependencyInsight --dependency commons-codec --configuration scm`

## Tests
Skip tests: `gradle -x test build`

## Properties
Print properties: `gradle properties`
Set property: `gradle -Pabc=123 properties`
List available tasks:
```shell
gradle tasks
gradle tasks --all
```
Specify project dir: `gradle -p /my/project/dir build`

## Deploy
Publish artifact to Maven local repo: `gradle publishToMavenLocal`

## Toolchain
List toolchains: `gradle javaToolchains`
