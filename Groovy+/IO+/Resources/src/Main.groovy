println(getClass().getResource('resource.txt'))

String content = getClass().getResource('resource.txt').text
println(content)

//Не работает!
/*
File file = new File(getClass().getResource('resource.txt').getFile())
String content2 = file.text
println(file.absolutePath)
println(content2)
*/