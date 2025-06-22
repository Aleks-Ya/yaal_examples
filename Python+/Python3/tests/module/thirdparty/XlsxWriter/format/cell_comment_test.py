from xlsxwriter import Workbook
from xlsxwriter.worksheet import Worksheet


def test_write_comment(workbook: Workbook):
    worksheet: Worksheet = workbook.add_worksheet()
    worksheet.write_string("A1", "Username")
    worksheet.write_comment("A1", "User's login")
    worksheet.write_string("B1", "Repository")
    worksheet.write_comment("B1", "User's repository")
    workbook.close()
