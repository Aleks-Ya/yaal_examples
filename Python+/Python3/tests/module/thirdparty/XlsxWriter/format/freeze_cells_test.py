from xlsxwriter import Workbook
from xlsxwriter.worksheet import Worksheet


def test_freeze_top_row(workbook: Workbook):
    worksheet: Worksheet = workbook.add_worksheet()
    worksheet.write_string("A1", "Username")
    worksheet.write_string("B1", "Age")
    worksheet.freeze_panes(1, 0)
    row_number: int = 100
    for row in range(1, row_number):
        worksheet.write_string(row=row, col=0, string=f"John {row}")
        worksheet.write_number(row=row, col=1, number=30)
    workbook.close()
