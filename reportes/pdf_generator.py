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


def title(title_text):
    title_table = Table(number_of_rows=1, number_of_columns=1)
    title_table.add(Paragraph(title_text, font="Helvetica-Bold"))
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
            Paragraph(subject['nombre'].capitalize().replace('_', ' '), horizontal_alignment=Alignment.CENTERED))
        subject_table.add(
            Paragraph(str(subject['carrera'].capitalize()), horizontal_alignment=Alignment.CENTERED))
        subject_table.add(
            Paragraph(str(subject['añoMateria']), horizontal_alignment=Alignment.CENTERED))
        subject_table.add(
            Paragraph(str(subject['dia'].capitalize()), horizontal_alignment=Alignment.CENTERED))
        time = str(subject['horaInicio'] + " a " + subject['horaFinalizacion'])
        subject_table.add(
            Paragraph(time, horizontal_alignment=Alignment.CENTERED))
        subject_table.add(
            Paragraph(str(subject['turno'].capitalize()), horizontal_alignment=Alignment.CENTERED))

    subject_table.set_padding_on_all_cells(
        Decimal(1), Decimal(1), Decimal(1), Decimal(1))
    return subject_table


def academic_record_table_generator(qualifications):
    academic_record_table = Table(number_of_rows=len(
        qualifications)+1, number_of_columns=3)

    academic_record_table.add(
        Paragraph("Materia", horizontal_alignment=Alignment.CENTERED))
    academic_record_table.add(
        Paragraph("Nota Examen", horizontal_alignment=Alignment.CENTERED))
    academic_record_table.add(
        Paragraph("Nota Final", horizontal_alignment=Alignment.CENTERED))

    for subject in qualifications:
        academic_record_table.add(
            Paragraph(subject['nombreMateria'].capitalize().replace('_', ' '), horizontal_alignment=Alignment.CENTERED))
        academic_record_table.add(
            Paragraph(str(subject['notaExamen']), horizontal_alignment=Alignment.CENTERED))
        academic_record_table.add(
            Paragraph(str(subject['notaFinal']), horizontal_alignment=Alignment.CENTERED))

    academic_record_table.set_padding_on_all_cells(
        Decimal(1), Decimal(1), Decimal(1), Decimal(1))
    return academic_record_table


def final_exams_table_generator(final_exams):
    final_exams_table = Table(number_of_rows=len(
        final_exams)+1, number_of_columns=4)

    final_exams_table.add(
        Paragraph("Materia", horizontal_alignment=Alignment.CENTERED))
    final_exams_table.add(
        Paragraph("Día", horizontal_alignment=Alignment.CENTERED))
    final_exams_table.add(
        Paragraph("Hora", horizontal_alignment=Alignment.CENTERED))
    final_exams_table.add(
        Paragraph("Docente", horizontal_alignment=Alignment.CENTERED))

    for final_exam in final_exams:
        final_exams_table.add(
            Paragraph(final_exam['materia'].capitalize().replace('_', ' '), horizontal_alignment=Alignment.CENTERED))
        final_exams_table.add(
            Paragraph(str(final_exam['dia']), horizontal_alignment=Alignment.CENTERED))
        final_exams_table.add(
            Paragraph(str(final_exam['hora']), horizontal_alignment=Alignment.CENTERED))
        final_exams_table.add(
            Paragraph(str(final_exam['docente']), horizontal_alignment=Alignment.CENTERED))

    final_exams_table.set_padding_on_all_cells(
        Decimal(1), Decimal(1), Decimal(1), Decimal(1))
    return final_exams_table


def subject_students_table_generator(students):
    subject_students_table = Table(number_of_rows=len(
        students)+1, number_of_columns=3)

    subject_students_table.add(
        Paragraph("Nombre", horizontal_alignment=Alignment.CENTERED))
    subject_students_table.add(
        Paragraph("DNI", horizontal_alignment=Alignment.CENTERED))
    subject_students_table.add(
        Paragraph("Email", horizontal_alignment=Alignment.CENTERED))

    for student in students:
        subject_students_table.add(
            Paragraph(student['estudiante'].capitalize().replace('_', ' '), horizontal_alignment=Alignment.CENTERED))
        subject_students_table.add(
            Paragraph(str(student['dni']), horizontal_alignment=Alignment.CENTERED))
        subject_students_table.add(
            Paragraph(str(student['email']), horizontal_alignment=Alignment.CENTERED))

    subject_students_table.set_padding_on_all_cells(
        Decimal(1), Decimal(1), Decimal(1), Decimal(1))
    return subject_students_table


def subjects_by_quarter_and_year_pdf_generator(quarter, quarter_year, quarter_subjects):
    pdf = Document()
    page = Page()
    pdf.insert_page(page)
    page_layout = SingleColumnLayout(page)

    title_text = "Materias de " + quarter + " cuatrimestre del año " + quarter_year

    page_layout.add(title(title_text))
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


def subjects_by_quarter_pdf_generator(quarter, shift, quarter_subjects):
    pdf = Document()
    page = Page()
    pdf.insert_page(page)
    page_layout = SingleColumnLayout(page)

    title_text = "Materias de " + quarter + " cuatrimestre del turno " + shift

    page_layout.add(title(title_text))
    page_layout.add(Paragraph(" "))
    page_layout.add(subjects(quarter_subjects))

    # store the PDF
    with open(Path("listado_de_materias_por_cuatrimestre_por_turno.pdf"), "wb") as pdf_file_handle:
        PDF.dumps(pdf_file_handle, pdf)

    with open("listado_de_materias_por_cuatrimestre_por_turno.pdf", "rb") as pdf_file:
        encoded_pdf = base64.b64encode(pdf_file.read())

    file = Path("listado_de_materias_por_cuatrimestre_por_turno.pdf")
    file.unlink()

    return encoded_pdf


def academic_record_pdf_generator(student_name, qualifications):
    pdf = Document()
    page = Page()
    pdf.insert_page(page)
    page_layout = SingleColumnLayout(page)

    title_text = f"Analítico del alumno {student_name}"

    page_layout.add(title(title_text))
    page_layout.add(Paragraph(" "))
    page_layout.add(academic_record_table_generator(qualifications))

    # store the PDF
    with open(Path("analítico.pdf"), "wb") as pdf_file_handle:
        PDF.dumps(pdf_file_handle, pdf)

    with open("analítico.pdf", "rb") as pdf_file:
        encoded_pdf = base64.b64encode(pdf_file.read())

    file = Path("analítico.pdf")
    file.unlink()

    return encoded_pdf


def final_exams_pdf_generator(final_exams):
    pdf = Document()
    page = Page()
    pdf.insert_page(page)
    page_layout = SingleColumnLayout(page)

    title_text = "Llamado a mesa de examenes finales"

    page_layout.add(title(title_text))
    page_layout.add(Paragraph(" "))
    page_layout.add(final_exams_table_generator(final_exams))

    # store the PDF
    with open(Path("llamado_finales.pdf"), "wb") as pdf_file_handle:
        PDF.dumps(pdf_file_handle, pdf)

    with open("llamado_finales.pdf", "rb") as pdf_file:
        encoded_pdf = base64.b64encode(pdf_file.read())

    file = Path("llamado_finales.pdf")
    file.unlink()

    return encoded_pdf


def subject_students_pdf_generator(subject, students):
    pdf = Document()
    page = Page()
    pdf.insert_page(page)
    page_layout = SingleColumnLayout(page)

    title_text = f"Listado de alumnos inscriptos a la cursada de {subject['nombre'].capitalize()}"

    page_layout.add(title(title_text))
    page_layout.add(Paragraph(" "))
    page_layout.add(subject_students_table_generator(students))

    # store the PDF
    with open(Path("estudiantes_inscriptos_a_materia.pdf"), "wb") as pdf_file_handle:
        PDF.dumps(pdf_file_handle, pdf)

    with open("estudiantes_inscriptos_a_materia.pdf", "rb") as pdf_file:
        encoded_pdf = base64.b64encode(pdf_file.read())

    file = Path("estudiantes_inscriptos_a_materia.pdf")
    file.unlink()

    return encoded_pdf


def final_exam_students_pdf_generator(subject, students):
    pdf = Document()
    page = Page()
    pdf.insert_page(page)
    page_layout = SingleColumnLayout(page)

    title_text = f"Listado de alumnos inscriptos al examen final de {subject['nombre'].capitalize()}"

    page_layout.add(title(title_text))
    page_layout.add(Paragraph(" "))
    page_layout.add(subject_students_table_generator(students))

    # store the PDF
    with open(Path("estudiantes_inscriptos_a_final.pdf"), "wb") as pdf_file_handle:
        PDF.dumps(pdf_file_handle, pdf)

    with open("estudiantes_inscriptos_a_final.pdf", "rb") as pdf_file:
        encoded_pdf = base64.b64encode(pdf_file.read())

    file = Path("estudiantes_inscriptos_a_final.pdf")
    file.unlink()

    return encoded_pdf
