// Create a Map
const fruits = new Map([
    ["apples", 500],
    ["bananas", 300],
    ["oranges", 200]
  ]);
console.log(fruits)

// Array to map
const array = ['key1:100', 'key2:200', 'key3:300']
const map = new Map(array.map(element => {
    const parts = element.split(':')
    const key = parts[0]
    const value = parseInt(parts[1])
    return [key, value]
}))
console.log(map)