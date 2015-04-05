//1
def myConst = 5
def incByConst = { num -> num + myConst }
println incByConst(10) // => 15

//2        
def localMethod() {
    def localVariable = new java.util.Date()
    return { println localVariable }
}        
    
private void freeVariables2() {
    def clos = localMethod()
    println "Executing the Closure:"
    clos()  
}