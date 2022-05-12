const list = ['a', 'b', 'c']

// List to string
const str = 'List: ' + list
console.assert(str === 'List: a,b,c')

// Get element by index
const element0 = list[0]
console.assert(element0 === 'a')

// Iterate list
for (let element of list) {
    console.log(element)
}


