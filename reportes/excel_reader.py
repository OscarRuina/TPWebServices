from pathlib import Path
import pandas as pd


def check_final_exam_students_qualifications_excel_format(excel_data):
    if 'Nombre' not in excel_data or 'Dni' not in excel_data or 'Email' not in excel_data or 'Examen final' not in excel_data:
        raise Exception('Excel file does not have the correct format')


def check_students_subject_qualifications_excel_format(excel_data):
    if 'Nombre' not in excel_data or 'DNI' not in excel_data or 'Email' not in excel_data or 'Primer parcial' not in excel_data or 'Segundo parcial' not in excel_data:
        raise Exception('Excel file does not have the correct format')


def final_exam_students_qualifications_excel_parser(filename):

    excel_data = pd.read_excel(
        filename, 'Hoja 1', index_col=None, na_values=["NA"])

    data = []

    check_final_exam_students_qualifications_excel_format(excel_data)

    for row in range(len(excel_data['Nombre'])):
        data.append(
            {
                'nombre': excel_data['Nombre'][row],
                'dni': excel_data['Dni'][row],
                'email': excel_data['Email'][row],
                'examen_final': excel_data['Examen final'][row],
                'nota_final': excel_data['Nota final'][row] or 0
            }
        )

    file = Path(filename)
    file.unlink()

    return data


def students_subject_qualifications_excel_parser(filename):

    excel_data = pd.read_excel(
        filename, 'Hoja 1', index_col=None, na_values=["NA"])

    data = []

    check_students_subject_qualifications_excel_format(excel_data)

    for row in range(len(excel_data['Nombre'])):
        data.append(
            {
                'nombre': excel_data['Nombre'][row],
                'dni': excel_data['DNI'][row],
                'email': excel_data['Email'][row],
                'primer_parcial': excel_data['Primer parcial'][row],
                'segundo_parcial': excel_data['Segundo parcial'][row],
                'cursada': excel_data['Cursada'][row] or 0
            }
        )

    file = Path(filename)
    file.unlink()

    return data
