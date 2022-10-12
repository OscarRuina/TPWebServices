from pathlib import Path
from borb.pdf import PDF
from borb.pdf import Document
from borb.pdf.page.page import Page
from borb.pdf.canvas.layout.page_layout.multi_column_layout import SingleColumnLayout
from borb.pdf.canvas.layout.table.fixed_column_width_table import FixedColumnWidthTable as Table
from borb.pdf.canvas.layout.text.paragraph import Paragraph
from borb.pdf.canvas.layout.layout_element import Alignment
from decimal import Decimal
import base64


def title(quarter, quarter_year):
    title_table = Table(number_of_rows=1, number_of_columns=1)
    title_table.add(Paragraph("Materias del " + quarter +
                    " cuatrimestre del año " + quarter_year, font="Helvetica-Bold"))
    title_table.no_borders()
    return title_table


def subjects(quarter_subjects):
    subject_table = Table(number_of_rows=len(
        quarter_subjects)+1, number_of_columns=6)

    subject_table.add(
        Paragraph("Nombre", horizontal_alignment=Alignment.CENTERED))
    subject_table.add(
        Paragraph("Carrera", horizontal_alignment=Alignment.CENTERED))
    subject_table.add(Paragraph("Año de materia",
                      horizontal_alignment=Alignment.CENTERED))
    subject_table.add(Paragraph("Día de cursada",
                      horizontal_alignment=Alignment.CENTERED))
    subject_table.add(
        Paragraph("Horario", horizontal_alignment=Alignment.CENTERED))
    subject_table.add(
        Paragraph("Turno", horizontal_alignment=Alignment.CENTERED))

    for subject in quarter_subjects:
        subject_table.add(
            Paragraph(subject['nombre'], horizontal_alignment=Alignment.CENTERED))
        subject_table.add(
            Paragraph(str(subject['carrera']), horizontal_alignment=Alignment.CENTERED))
        subject_table.add(
            Paragraph(str(subject['añoMateria']), horizontal_alignment=Alignment.CENTERED))
        subject_table.add(
            Paragraph(str(subject['dia']), horizontal_alignment=Alignment.CENTERED))
        time = str(subject['horaInicio'] + " a " + subject['horaFinalizacion'])
        subject_table.add(
            Paragraph(time, horizontal_alignment=Alignment.CENTERED))
        subject_table.add(
            Paragraph(str(subject['turno']), horizontal_alignment=Alignment.CENTERED))

    subject_table.set_padding_on_all_cells(
        Decimal(1), Decimal(1), Decimal(1), Decimal(1))
    return subject_table


def subjects_by_quarter_pdf_generator(quarter, quarter_year, quarter_subjects):
    pdf = Document()
    page = Page()
    pdf.insert_page(page)
    page_layout = SingleColumnLayout(page)
    page_layout.add(title(quarter, quarter_year))
    page_layout.add(Paragraph(" "))
    page_layout.add(subjects(quarter_subjects))

    # store the PDF
    with open(Path("listado_de_materias_por_cuatrimestre.pdf"), "wb") as pdf_file_handle:
        PDF.dumps(pdf_file_handle, pdf)

    with open("listado_de_materias_por_cuatrimestre.pdf", "rb") as pdf_file:
        encoded_pdf = base64.b64encode(pdf_file.read())

    file = Path("listado_de_materias_por_cuatrimestre.pdf")
    file.unlink()

    return encoded_pdf