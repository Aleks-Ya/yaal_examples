# JaCoCo CLI

## Prepare
1. Download ZIP from last release: https://github.com/jacoco/jacoco/releases
2. Unpack `lib/jacococli.jar` to `/home/aleks/installed/JacocoCli/jacococli.jar`
3. Add alias to `~/.bashrc`: `alias jacoco='java -jar /home/aleks/installed/JacocoCli/jacococli.jar'`
4. Test: `jacoco --help`

## Commands
Help: `jacoco --help`
Version: `jacoco version`
Print content of an `exec`-file ("execution" file): `jacoco execinfo build/jacoco/test.exec`

## Make report manually
1. Clean: `rm -rf jacoco.exec out_prod out_report out_test`
2. Compile prod classes: `javac -d out_prod src_prod/**/*.java`
3. Compile test class: `javac -cp /media/aleks/ADATA/home/.m2/repository/org/junit/jupiter/junit-jupiter-api/5.9.1/junit-jupiter-api-5.9.1.jar:out_prod -d out_test src_test/**/*.java`
4. Run test: `java -javaagent:/home/aleks/installed/JacocoCli/jacocoagent.jar -jar /home/aleks/installed/JUnitConsoleLauncher/junit-platform-console-standalone.jar -cp out_prod:out_test --scan-classpath`
5. Generate report: `java -jar /home/aleks/installed/JacocoCli/jacococli.jar report jacoco.exec --classfiles out_prod --sourcefiles src_prod --html out_report`
