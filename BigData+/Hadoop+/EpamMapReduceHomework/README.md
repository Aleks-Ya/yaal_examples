INPUT FILES:
- `files/book/All_Is_Well.txt`
- `files/book/The_Genius.txt`

BUILD
Run: `mvn clean package`
Result: `target/LongestWord-jar-with-dependencies.jar`

EXECUTE
Prepare: put input files to HDFS
Run: `java -jar target/LongestWord-jar-with-dependencies.jar hdfs://localhost:9000 /tmp/longest_word/The_Genius.txt /tmp/longest_word/results`
Result from file is duplicated to console: e.g. `THE LONGEST WORD: penny-wise-and-pound-foolish`

LOGS:
- `files/log/all_is_well.log`
- `files/log/the_genius.log`
