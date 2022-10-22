from flask import Flask, request, Response
from flask_cors import CORS
from pdf_generator import subjects_by_quarter_and_year_pdf_generator, subjects_by_quarter_pdf_generator, academic_record_pdf_generator, final_exams_pdf_generator
import json
import requests
import logging


app = Flask(__name__)
cors = CORS(app)
app.config['CORS_HEADERS'] = 'Content-Type'


@app.route("/pdf/materias", methods=["GET"])
def pdf_download():
    try:
        subject_data = requests.get(
            'http://localhost:8081/api/materias').json()

        quarter = request.args.get('cuatrimestre')
        subject_year = request.args.get('añoCuatrimestre')
        quarter_subjects = []

        for subject in subject_data:
            if quarter in str(subject['cuatrimestre']) and subject_year in str(subject['añoMateria']):
                quarter_subjects.append(subject)

        encoded_pdf = subjects_by_quarter_and_year_pdf_generator(
            quarter.lower(), subject_year.lower(), quarter_subjects)
        return Response(encoded_pdf, status=200, mimetype='application/json')

    except Exception as e:
        return Response(json.dumps({"error": str(e)}), status=500, mimetype='application/json')


@app.route("/pdf/materias-turno", methods=["GET"])
def quarter_shift_subjects():
    try:
        subject_data = requests.get(
            'http://localhost:8081/api/materias').json()

        quarter = request.args.get('cuatrimestre')
        shift = request.args.get('turno')

        quarter_subjects = []

        for subject in subject_data:
            if quarter in str(subject['cuatrimestre']) and shift in str(subject['turno']):
                quarter_subjects.append(subject)

        sorted_quarter_subjects = sorted(
            quarter_subjects, key=lambda d: d['añoMateria'])

        encoded_pdf = subjects_by_quarter_pdf_generator(
            quarter.lower(), shift.lower(), sorted_quarter_subjects)
        return Response(encoded_pdf, status=200, mimetype='application/json')

    except Exception as e:
        return Response(json.dumps({"error": str(e)}), status=500, mimetype='application/json')


@app.route("/pdf/analitico", methods=["GET"])
def academic_record():
    try:
        student_id = request.args.get('idEstudiante')

        student_data = requests.get(
            f'http://localhost:8081/api/usuarios/{student_id}').json()

        student_name = f'{student_data["nombre"]} {student_data["apellido"]}'

        qualifications = requests.get(
            f'http://localhost:8081/api/usuarios/{student_id}/materiasEstudiante').json()

        encoded_pdf = academic_record_pdf_generator(
            student_name, qualifications)

        return Response(encoded_pdf, status=200, mimetype='application/json')

    except Exception as e:
        return Response(json.dumps({"error": str(e)}), status=500, mimetype='application/json')


@app.route("/pdf/llamado-final", methods=["GET"])
def final_exams_pdf():
    try:
        final_exams = requests.get(
            f'http://localhost:8081/api/mesas-examen').json()

        encoded_pdf = final_exams_pdf_generator(final_exams)

        return Response(encoded_pdf, status=200, mimetype='application/json')

    except Exception as e:
        return Response(json.dumps({"error": str(e)}), status=500, mimetype='application/json')


if __name__ == '__main__':
    logging.basicConfig()
    app.run(debug=True)
