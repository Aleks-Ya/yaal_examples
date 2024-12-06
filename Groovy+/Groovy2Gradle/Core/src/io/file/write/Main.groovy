package io.file.write

def file = File.createTempFile("temp",".tmp")
file.deleteOnExit() 
println(file.absolutePath)
file.withWriter { out ->
   out.writeLine("Hello, File!")
}