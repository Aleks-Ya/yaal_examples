package regex

import java.util.regex.Pattern

//final String s = "VALUES (1, TRUE, TRUE, FALSE, 'Standart', FALSE, FALSE, 10, 30, 100, 3000000, 99999900, 'Касса в линейке', FALSE, FALSE, TRUE, FALSE, TRUE, 30, 1, 'cash_in;', 'cash_out;z_report;');"
final s = "INSERT INTO cash_template \n VALUES ('Касса в линейке', 'cash_in;', 'cash_out;z_report;'); \n EXIT;"
def p = Pattern.compile('^.*(cash_in).*$')
def m = p.matcher(s)
m.matches()
println(m.groupCount())
for (int i = 1; i <= m.groupCount(); i++) {
    println(m.group(i))
}
//String result = s.(, 'Касса в отделе')
//println(result)