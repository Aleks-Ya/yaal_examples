# find CLI

Find `*.java` files from current directory recursively: `find . -name *.java`
Find files by name (case insensitive): `find . -iname "*.jpg"`
Find class in JAR files: `find ./lib -name "*.jar" -exec sh -c 'jar -tf {}|grep -H --label {} 'org.apache.commons.io.FileUtils'' \;`
Hide "Permission denied" error: `find . -name *.java 2>&-`
Execute a command against each file: `find . -iname "*.jpg" -exec basename {} \;`
Rertun only end filename: `find . -iname "*.jpg" -exec basename {} \;`
