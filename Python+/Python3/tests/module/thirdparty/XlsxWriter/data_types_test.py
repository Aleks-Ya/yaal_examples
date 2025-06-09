from xlsxwriter import Workbook
from xlsxwriter.worksheet import Worksheet


def test_string(workbook: Workbook):
    worksheet: Worksheet = workbook.add_worksheet()
    worksheet.write_string(row=1, col=0, string="Username")
    worksheet.write_string(row=1, col=1, string="Repository")
    workbook.close()


def test_url(workbook: Workbook):
    worksheet: Worksheet = workbook.add_worksheet()
    worksheet.write_url('A1', 'http://www.example.com', string='Example Link')
    workbook.close()
