package regex.group

String coordinates = 'ru.yaal:maven:jar:jar-with-dependencies:1-SNAPSHOT'
def pattern = ~"^([\\w.]+):([\\w.]+):([\\w.]+):([\\w.]+):([\\w.-]+)\$"
def matcher = pattern.matcher(coordinates)
while (matcher.find()) {
    assert matcher.group(1) == 'ru.yaal'
    assert matcher.group(2) == 'maven'
    assert matcher.group(3) == 'jar'
    assert matcher.group(4) == 'jar-with-dependencies'
    assert matcher.group(5) == '1-SNAPSHOT'
}