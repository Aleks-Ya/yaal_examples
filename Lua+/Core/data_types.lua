-- Integer Number
num_integer = 1
print('Integer Number: ' .. num_integer)

-- Float Number
num_float = 1.5
print('Float Number: ' .. num_float)

-- String
str = "funky"
print('String: ' .. str)

-- Boolean
bool = true
print('Boolean: ' .. tostring(bool))

-- Nil
n = nil
print('Nil: ' .. tostring(n))

-- Function
f = function(x,y) return x+y end
print('Function: ' .. tostring(f))

-- Array
a = {"a", "b", "c"}
print('Array (=Table): ' .. tostring(a))

-- Table
t = {a = 1, b = 2}
print('Table: ' .. tostring(t))
