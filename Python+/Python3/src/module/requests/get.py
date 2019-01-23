# Send GET HTTP request
import requests

response = requests.get('http://www.cbr.ru/statistics/UDStat.aspx?TblID=3-2&Year=2009&Month=01&ExportToExcel=Y')
assert response.status_code == 200
assert len(response.content) > 0
