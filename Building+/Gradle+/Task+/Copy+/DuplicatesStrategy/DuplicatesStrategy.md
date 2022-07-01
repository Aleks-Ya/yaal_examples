# Use DuplicatesStrategy for overriding files

## Available duplicates strategies
Enum: https://docs.gradle.org/current/javadoc/org/gradle/api/file/DuplicatesStrategy.html
- `inherit` (default, refers to `include`)
- `exclude`
- `fail`
- `include`
- `warn`

## Run
Run: `gradle`
Result in file: `build/copied/people.txt`
