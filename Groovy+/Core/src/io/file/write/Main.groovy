package io.file.write

File file = File.createTempFile("temp",".tmp")
file.deleteOnExit() 
println(file.absolutePath)
file.withWriter { out ->
   out.writeLine("Hello, File!")
}