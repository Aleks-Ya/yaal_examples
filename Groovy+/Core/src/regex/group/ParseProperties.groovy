package regex.group

String s = '''
    abc=100
    xyz=300
'''
def key = 'abc'
def p = ~"${key}=(\\d+)"
def m = p.matcher(s)
//def m = s =~ "${key}=(\\d+)"
while (m.find()) {
    println(m.group(1))
}