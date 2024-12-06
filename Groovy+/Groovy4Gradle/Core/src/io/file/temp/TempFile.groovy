package io.file.temp

File.createTempFile("temp",".scrap").with {
    deleteOnExit()
    write "Hello world"
    println absolutePath
}