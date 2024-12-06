package closure

def list = ['a','b','c','d']
def newList = []

list.collect( newList ) {
  it.toUpperCase()
}
println newList       