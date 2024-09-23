# PowerShell

## Install
Linux: `sudo snap install powershell --classic`
Windows 10: `winget install Microsoft.PowerShell`

## Info
Version: `$PSVersionTable.PSVersion`
Help: `Get-Help`
Help about a command: `Get-Help Get-Process`

## DNS
Resolve DNS name to IP address: `Resolve-DnsName -Name www.bing.com`
Resolve DNS name against specific DNS server: `Resolve-DnsName -Name www.bing.com -Server 10.0.0.1`

## File
Tail: `Get-Content -Path out.log -Tail 10`
Tail and follow: `Get-Content -Path out.log -Tail 10 -Wait`
