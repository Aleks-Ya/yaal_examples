from xlsxwriter import Workbook
from xlsxwriter.format import Format
from xlsxwriter.worksheet import Worksheet

from app.addon_catalog.common.data_types import Aggregation


class AggregationSheet:
    __header_row: int = 0
    __property_col: int = 0
    __value_col: int = 1

    @staticmethod
    def create_sheet(workbook: Workbook, aggregation: Aggregation) -> None:
        worksheet: Worksheet = workbook.add_worksheet(name="Addons")
        AggregationSheet.__set_column_width(worksheet)
        AggregationSheet.__add_header(workbook, worksheet)
        AggregationSheet.__add_rows(aggregation, worksheet)

    @staticmethod
    def __set_column_width(worksheet: Worksheet) -> None:
        worksheet.set_column(AggregationSheet.__property_col, AggregationSheet.__property_col, 40)
        worksheet.set_column(AggregationSheet.__value_col, AggregationSheet.__value_col, 20)

    @staticmethod
    def __add_header(workbook: Workbook, worksheet: Worksheet) -> None:
        header_format: Format = workbook.add_format({"bold": True, 'align': 'center'})
        worksheet.write_string(AggregationSheet.__header_row, AggregationSheet.__property_col, "Property",
                               header_format)
        worksheet.write_string(AggregationSheet.__header_row, AggregationSheet.__value_col, "Value", header_format)

    @staticmethod
    def __add_rows(aggregation: Aggregation, worksheet: Worksheet) -> None:
        values: dict[str, int] = {
            "Addon number": aggregation.addon_number,
            "Addon with GitHub number": aggregation.addon_with_github_number,
            "Addon with Anki forum page number": aggregation.addon_with_anki_forum_page_number,
            "Addon with unit-tests number": aggregation.addon_with_unit_tests_number
        }
        for i, (name, value) in enumerate(values.items()):
            row: int = AggregationSheet.__header_row + i + 1
            worksheet.write_string(row, AggregationSheet.__property_col, name)
            worksheet.write_number(row, AggregationSheet.__value_col, value)
