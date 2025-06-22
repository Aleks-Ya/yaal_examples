from xlsxwriter import Workbook
from xlsxwriter.worksheet import Worksheet


def test_merge_range_string(workbook: Workbook):
    worksheet: Worksheet = workbook.add_worksheet()
    worksheet.merge_range('A1:B1', 'GitHub')
    worksheet.write_string("A2", "Username")
    worksheet.write_string("B2", "Repository")
    workbook.close()


def test_merge_range_number(workbook: Workbook):
    worksheet: Worksheet = workbook.add_worksheet()
    worksheet.merge_range(first_row=0, last_row=0, first_col=0, last_col=1, data="GitHub")
    worksheet.write_string(row=1, col=0, string="Username")
    worksheet.write_string(row=1, col=1, string="Repository")
    workbook.close()
