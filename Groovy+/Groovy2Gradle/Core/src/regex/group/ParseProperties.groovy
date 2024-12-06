package regex.group

def string = '''
    abc=100
    xyz=300
'''
def key = 'abc'
def pattern = ~"${key}=(\\d+)"
def matcher = pattern.matcher(string)
//def matcher = string =~ "${key}=(\\d+)"
while (matcher.find()) {
    assert matcher.group(1) == '100'
}