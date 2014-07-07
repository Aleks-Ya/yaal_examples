def list = ['a','b','c','d']
def newList = []

def clos = { it.toUpperCase() }
list.collect( newList, clos )

assert newList == ["A", "B", "C", "D"]