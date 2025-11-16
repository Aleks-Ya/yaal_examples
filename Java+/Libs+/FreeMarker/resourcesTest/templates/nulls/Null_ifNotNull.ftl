<#-- Top level variables -->
<#if name??>name is not null<#else>name is null</#if>
<#if title??>title is not null<#else>title is null</#if>

<#-- Non-top level variables -->
<#if (name.surname)??>name or surname is not null<#else>name or surname is null</#if>