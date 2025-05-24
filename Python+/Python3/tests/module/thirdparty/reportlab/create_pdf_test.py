from reportlab.pdfgen import canvas
from reportlab.pdfgen.canvas import Canvas

from conftest import TempPath


def test_create_pdf():
    pdf_file: str = str(TempPath.temp_path_absent(".pdf"))
    c: Canvas = canvas.Canvas(pdf_file)
    c.drawString(100, 750, "Hello, PDF!")
    c.save()
