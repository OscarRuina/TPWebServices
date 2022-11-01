import pandas as pd
from pathlib import Path
import base64
import requests


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


def students_subject_qualifications_excel_generator(subject_id, students):

    names_data = []
    dni_data = []
    email_data = []
    first_exam = []
    second_exam = []
    course_qualification = []
    subject_name = None

    for student in students:
        names_data.append(student['estudiante'].capitalize().replace('_', ' '))
        dni_data.append(str(student['dni']))
        email_data.append(str(student['email']))
        student_id = student['id']

        qualifications = requests.get(
            f'http://admin:8081/api/usuarios/{student_id}/materiasEstudiante').json()

        subject_qualifications = next(
            (item for item in qualifications if int(item['id']) == int(subject_id)), None)

        subject_name = subject_qualifications['nombreMateria']
        first_exam.append(subject_qualifications['notaParcial1'])
        second_exam.append(subject_qualifications['notaParcial2'])
        course_qualification.append(subject_qualifications['notaCursada'])

    df = pd.DataFrame(
        {
            'Nombre': names_data,
            'DNI': dni_data,
            'Email': email_data,
            'Primer parcial': first_exam,
            'Segundo parcial': second_exam,
            'Cursada': course_qualification
        }
    )

    writer = pd.ExcelWriter(
        f"estudiantes_inscriptos_a_materia_{subject_name.capitalize()}.xlsx", engine='xlsxwriter')

    df.to_excel(writer, sheet_name='Hoja 1', index=False)

    writer.close()

    with open(f"estudiantes_inscriptos_a_materia_{subject_name.capitalize()}.xlsx", "rb") as excel_file:
        encoded_excel = base64.b64encode(excel_file.read())

    file = Path(
        f"estudiantes_inscriptos_a_materia_{subject_name.capitalize()}.xlsx")
    file.unlink()

    return encoded_excel


def final_exam_students_excel_generator(subject, students):

    names_data = []
    dni_data = []
    email_data = []

    for student in students:
        names_data.append(student['estudiante'])
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
    file.unlink()

    return encoded_excel


def final_exam_students_qualifications_excel_generator(subject_name, final_exam_students):

    student_name = []
    final_exam_qualification = []
    final_subject_qualification = []
    dni_data = []
    email_data = []

    for student in final_exam_students:
        student_name.append(student['estudiante'])
        dni_data.append(student['dni'])
        email_data.append(student['email'])
        final_exam_qualification.append(0)
        final_subject_qualification.append(0)

    df = pd.DataFrame(
        {'Nombre': student_name, 'Dni': dni_data, 'Email': email_data, 'Examen final': final_exam_qualification, 'Nota final': final_subject_qualification})

    writer = pd.ExcelWriter(
        f"estudiantes_inscriptos_a_final_{subject_name.capitalize().replace('_', ' ')}.xlsx", engine='xlsxwriter')

    df.to_excel(writer, sheet_name='Hoja 1', index=False)

    writer.close()

    with open(f"estudiantes_inscriptos_a_final_{subject_name.capitalize().replace('_', ' ')}.xlsx", "rb") as excel_file:
        encoded_excel = base64.b64encode(excel_file.read())

    file = Path(
        f"estudiantes_inscriptos_a_final_{subject_name.capitalize().replace('_', ' ')}.xlsx")
    file.unlink()

    return encoded_excel
