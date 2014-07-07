class Class1 {
  def closure = {
    println this.class.name
    println delegate.class.name
    def nestedClos = {
      println owner.class.name
    }
    nestedClos()
  }
}

def clos = new Class1().closure
clos.delegate = this
clos()