var json = '{"id": 1, "name": "John", "age": 30, "city": {"code": 7, "title": "Moscow"}}'
var person = JSON.parse(json)
var id = person.id
var name = person.name
var age = person.age
var city = person.city
var code = city.code
var title = city.title
console.log(id + '-' + name + '-' + age + '-' + code + '-' + title);