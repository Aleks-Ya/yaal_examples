<#-- @ftlvariable name="array" type="java.lang.String[]" -->
<#-- @ftlvariable name="list" type="java.util.List" -->
<#-- @ftlvariable name="boolean" type="java.lang.Boolean" -->
<#-- @ftlvariable name="decimal" type="java.lang.Double" -->
<#-- @ftlvariable name="integer" type="java.lang.Integer" -->
<#-- @ftlvariable name="string" type="java.lang.String" -->
String: ${string}
Integer: ${integer}
Decimal: ${decimal}
Boolean (true/false): ${boolean?c}
Boolean (custom): ${boolean?string("ok", "not ok")}
Date (time): ${date?time}
Date (date): ${date?date}
Date (datetime): ${date?datetime}
List:
Size: ${list?size}
<#list list as item>
${item}
</#list>
Array:
<#list array as item>
${item}
</#list>
Map (string_in_map): ${map.string_in_map}