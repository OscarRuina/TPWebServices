import pandas as pd
from pathlib import Path
import base64


def subject_students_excel_generator(subject, students):

    names_data = []
    dni_data = []
    email_data = []

    for student in students:
        names_data.append(student['estudiante'].capitalize().replace('_', ' '))
        dni_data.append(str(student['dni']))
        email_data.append(str(student['email']))

    df = pd.DataFrame(
        {'Nombre': names_data, 'DNI': dni_data, 'Email': email_data})

    writer = pd.ExcelWriter(
        f"estudiantes_inscriptos_a_materia_{subject['nombre'].capitalize()}.xlsx", engine='xlsxwriter')

    df.to_excel(writer, sheet_name='Hoja 1', index=False)

    writer.close()

    with open(f"estudiantes_inscriptos_a_materia_{subject['nombre'].capitalize()}.xlsx", "rb") as excel_file:
        encoded_excel = base64.b64encode(excel_file.read())

    file = Path(
        f"estudiantes_inscriptos_a_materia_{subject['nombre'].capitalize()}.xlsx")
    file.unlink()

    return encoded_excel


def final_exam_students_excel_generator(subject, students):

    names_data = []
    dni_data = []
    email_data = []

    for student in students:
        names_data.append(student['estudiante'].capitalize().replace('_', ' '))
        dni_data.append(str(student['dni']))
        email_data.append(str(student['email']))

    df = pd.DataFrame(
        {'Nombre': names_data, 'DNI': dni_data, 'Email': email_data})

    writer = pd.ExcelWriter(
        f"estudiantes_inscriptos_a_final_{subject['nombre'].capitalize()}.xlsx", engine='xlsxwriter')

    df.to_excel(writer, sheet_name='Hoja 1', index=False)

    writer.close()

    with open(f"estudiantes_inscriptos_a_final_{subject['nombre'].capitalize()}.xlsx", "rb") as excel_file:
        encoded_excel = base64.b64encode(excel_file.read())

    file = Path(
        f"estudiantes_inscriptos_a_final_{subject['nombre'].capitalize()}.xlsx")
    #file.unlink()

    return encoded_excel
