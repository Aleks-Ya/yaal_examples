# JUnit ConsoleLauncher

## Prepare
1. Download JAR: https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/
2. Put JAR with name: `/home/aleks/installed/JUnitConsoleLauncher/junit-platform-console-standalone.jar`
3. Add to `~/.bashrc`: `alias junit='java -jar /home/aleks/installed/JUnitConsoleLauncher/junit-platform-console-standalone.jar'`
4. Test: `junit -h`

## Commands
Help: `junit -h`
Run test: `junit -cp calculator.jar:out_test --scan-classpath`
