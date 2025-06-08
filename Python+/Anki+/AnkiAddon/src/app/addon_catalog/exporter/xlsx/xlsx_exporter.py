from pathlib import Path

from xlsxwriter import Workbook
from xlsxwriter.format import Format
from xlsxwriter.worksheet import Worksheet

from app.addon_catalog.common.data_types import AddonDetails


class XlsxExporter:
    __id_col: int = 0
    __title_col: int = 1
    __rating_col: int = 2
    __stars_col: int = 3
    __updated_col: int = 4
    __last_commit_col: int = 5

    def __init__(self, output_dir: Path):
        self.output_dir: Path = output_dir
        self.output_dir.mkdir(parents=True, exist_ok=True)

    def export(self, details_list: list[AddonDetails]):
        output_file: Path = self.output_dir / "anki-addon-catalog.xlsx"
        workbook: Workbook = Workbook(output_file)
        worksheet: Worksheet = workbook.add_worksheet(name="Addons")
        self.__set_column_width(worksheet)
        self.__add_header(workbook, worksheet)
        self.__add_rows(details_list, worksheet)
        workbook.close()
        print(f"Write XLSX to file: {output_file}")

    @staticmethod
    def __add_rows(details_list: list[AddonDetails], worksheet: Worksheet) -> None:
        for i, addon in enumerate(details_list):
            row: int = i + 1
            worksheet.write(row, XlsxExporter.__id_col, addon.header.id)
            worksheet.write(row, XlsxExporter.__title_col, addon.header.title)
            worksheet.write(row, XlsxExporter.__rating_col, addon.header.rating)
            worksheet.write(row, XlsxExporter.__stars_col, addon.stars)
            worksheet.write(row, XlsxExporter.__updated_col, addon.header.update_date)
            worksheet.write(row, XlsxExporter.__last_commit_col,
                            addon.last_commit.strftime("%Y-%m-%d") if addon.last_commit else "")

    @staticmethod
    def __set_column_width(worksheet: Worksheet) -> None:
        worksheet.set_column(XlsxExporter.__id_col, XlsxExporter.__id_col, 15)
        worksheet.set_column(XlsxExporter.__title_col, XlsxExporter.__title_col, 100)
        worksheet.set_column(XlsxExporter.__rating_col, XlsxExporter.__rating_col, 10)
        worksheet.set_column(XlsxExporter.__stars_col, XlsxExporter.__stars_col, 10)
        worksheet.set_column(XlsxExporter.__updated_col, XlsxExporter.__updated_col, 10)
        worksheet.set_column(XlsxExporter.__last_commit_col, XlsxExporter.__last_commit_col, 10)

    @staticmethod
    def __add_header(workbook: Workbook, worksheet: Worksheet) -> None:
        header_format: Format = workbook.add_format({"bold": True, 'align': 'center'})
        worksheet.write(0, XlsxExporter.__id_col, "ID", header_format)
        worksheet.write(0, XlsxExporter.__title_col, "Title", header_format)
        worksheet.write(0, XlsxExporter.__rating_col, "Rating", header_format)
        worksheet.write(0, XlsxExporter.__stars_col, "Stars", header_format)
        worksheet.write(0, XlsxExporter.__updated_col, "Updated", header_format)
        worksheet.write(0, XlsxExporter.__last_commit_col, "Last commit", header_format)
