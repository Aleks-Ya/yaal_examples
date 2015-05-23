String: ${string}
Integer: ${integer}
Decimal: ${decimal}
Boolean (true/false): ${boolean?c}
Boolean (custom): ${boolean?string("ok", "not ok")}
Date (time): ${date?time}
Date (date): ${date?date}
Date (datetime): ${date?datetime}
List:
<#list list as item>
${item}
</#list>
Array:
<#list array as item>
${item}
</#list>
Map (string_in_map): ${map.string_in_map}