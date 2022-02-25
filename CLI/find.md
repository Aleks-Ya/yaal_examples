# find CLI

Find *.java files from current directory recursively:
```
find . -name *.java
```
Find class in JAR files: 
```
find ./lib -name "*.jar" -exec sh -c 'jar -tf {}|grep -H --label {} 'org.apache.commons.io.FileUtils'' \;
```
Hide "Permission denied" error:
```
find . -name *.java 2>&-
```
