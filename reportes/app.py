from flask import Flask, request, Response
from flask_cors import CORS
from werkzeug.utils import secure_filename
from swagger_ui import api_doc
import json
import requests
import logging
import os
import xmltodict

from pdf_generator import subjects_by_quarter_and_year_pdf_generator, subjects_by_quarter_pdf_generator, academic_record_pdf_generator, final_exams_pdf_generator
from excel_generator import subject_students_excel_generator, final_exam_students_excel_generator, students_subject_qualifications_excel_generator, final_exam_students_qualifications_excel_generator
from excel_reader import final_exam_students_qualifications_excel_parser, students_subject_qualifications_excel_parser

ALLOWED_EXTENSIONS = {'xlsx'}
BASE_URL='http://admin:8081'

app = Flask(__name__)
cors = CORS(app)
app.config['CORS_HEADERS'] = 'Content-Type'
api_doc(app, config_path='./docs/swagger.yml',
        url_prefix='/api/docs', title='API doc')


def allowed_file(filename):
    return '.' in filename and \
           filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS


@app.route("/pdf/materias", methods=["GET"])
def pdf_download():
    try:
        subject_data = requests.get(
            BASE_URL+'/api/materias').json()

        quarter = request.args.get('cuatrimestre')
        subject_year = request.args.get('añoCuatrimestre')
        quarter_subjects = []

        for subject in subject_data:
            if quarter in str(subject['cuatrimestre']) and subject_year in str(subject['añoCuatrimestre']):
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
            BASE_URL+'/api/materias').json()

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
            f'http://admin:8081/api/usuarios/{student_id}').json()

        student_name = student_data['nombre'] + ' ' + student_data['apellido']

        payload = f"""<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                    xmlns:us="http://www.unla.com/estudiante/soapEstudiantes">
                    <soapenv:Header/>
                    <soapenv:Body>
                        <us:SolicitudIdEstudiante>
                            <us:id>{student_id}</us:id>
                        </us:SolicitudIdEstudiante>
                    </soapenv:Body>
                </soapenv:Envelope>"""

        headers = {
            'Content-Type': 'text/xml; charset=utf-8'
        }

        soap_response = requests.request(
            "POST", 'http://estudiante:8082/soapWS', headers=headers, data=payload)

        parsed_soap_response = xmltodict.parse(soap_response.content)

        qualifications = []
        qualifications_parsed = []

        if (type(parsed_soap_response['SOAP-ENV:Envelope']['SOAP-ENV:Body']['ns2:Analitico']['ns2:nota']) is not list):
            qualifications.append(
                parsed_soap_response['SOAP-ENV:Envelope']['SOAP-ENV:Body']['ns2:Analitico']['ns2:nota'])
        else:
            qualifications = parsed_soap_response['SOAP-ENV:Envelope']['SOAP-ENV:Body']['ns2:Analitico']['ns2:nota']

        for qualification in qualifications:
            qualifications_parsed.append(
                {
                    'nombreMateria': qualification['ns2:materia'],
                    'notaExamen': qualification['ns2:notaExamen'],
                    'notaFinal': qualification['ns2:notaFinal'],
                }
            )

        encoded_pdf = academic_record_pdf_generator(
            student_name, qualifications_parsed)

        return Response(encoded_pdf, status=200, mimetype='application/json')

    except Exception as e:
        return Response(json.dumps({"error": str(e)}), status=500, mimetype='application/json')


@app.route("/pdf/llamado-final", methods=["GET"])
def final_exams_pdf():
    try:
        final_exams = requests.get(
            f'http://admin:8081/api/mesas-examen').json()

        encoded_pdf = final_exams_pdf_generator(final_exams)

        return Response(encoded_pdf, status=200, mimetype='application/json')

    except Exception as e:
        return Response(json.dumps({"error": str(e)}), status=500, mimetype='application/json')


@app.route("/excel/cursada-materia", methods=["GET"])
def subject_students_excel():
    try:
        subject_id = request.args.get('idMateria')

        subject = requests.get(
            f'http://admin:8081/api/materias/{subject_id}').json()

        students = requests.get(
            f'http://admin:8081/api/materias/{subject_id}/estudiantes').json()

        encoded_excel = subject_students_excel_generator(subject, students)

        return Response(encoded_excel, status=200, mimetype='application/json')

    except Exception as e:
        return Response(json.dumps({"error": str(e)}), status=500, mimetype='application/json')


@app.route("/excel/cursada-materia-notas", methods=["GET"])
def subject_students_qualifications_excel():
    try:
        subject_id = request.args.get('idMateria')

        students = requests.get(
            f'http://admin:8081/api/materias/{subject_id}/estudiantes').json()

        encoded_excel = students_subject_qualifications_excel_generator(
            subject_id, students)

        return Response(encoded_excel, status=200, mimetype='application/json')

    except Exception as e:
        return Response(json.dumps({"error": str(e)}), status=500, mimetype='application/json')


@app.route("/excel/carga-cursada-materia-notas", methods=["POST"])
def subject_students_qualifications_excel_reader():
    try:
        subject_id = request.args.get('idMateria')
        teacher_id = request.args.get('idDocente')

        if 'file' not in request.files:
            raise Exception('Must send excel file (.xlsx)')
        file = request.files['file']
        if file.filename == '':
            raise Exception('Must select an excel file (.xlsx)')
        if file and allowed_file(file.filename):
            filename = secure_filename(file.filename)
            file.save(os.path.join(filename))
        else:
            raise Exception('File must be .xlsx')

        excel_data = students_subject_qualifications_excel_parser(filename)

        payload = f"""<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:us="http://www.unla.com/docente/soapDocentes">
                    <soapenv:Header/>
                    <soapenv:Body>
                        <us:SolicitudAlumnosCursada>
                            <us:idDocente>{teacher_id}</us:idDocente>
                            <us:idMateria>{subject_id}</us:idMateria>"""

        for excel_student in excel_data:

            students_data = requests.get(
                'http://admin:8081/api/usuarios?rol=ESTUDIANTE').json()

            student_data = None

            for json_student in students_data:
                if int(json_student['dni']) == int(excel_student['dni']):
                    student_data = json_student

                    payload = payload + f"""<us:NotaAlumnoCursada>
                                                <us:id>{student_data['id']}</us:id>
                                                <us:notaParcial1>{excel_student['primer_parcial']}</us:notaParcial1>
                                                <us:notaParcial2>{excel_student['segundo_parcial']}</us:notaParcial2>
                                            </us:NotaAlumnoCursada>"""

        payload = payload + """</us:SolicitudAlumnosCursada>
                        </soapenv:Body>
                    </soapenv:Envelope>"""

        headers = {
            'Content-Type': 'text/xml; charset=utf-8'
        }

        soap_response = requests.request(
            "POST", 'http://docente:8083/soapWS', headers=headers, data=payload)

        if soap_response.status_code == 200:
            return Response('Excel procesado correctamente.', status=200, mimetype='application/json')
        else:
            raise Exception(
                f'El Excel no fue procesado correctamente. {soap_response} - {soap_response.status_code}')

    except Exception as e:
        return Response(json.dumps({"error": str(e)}), status=500, mimetype='application/json')


@app.route("/excel/inscriptos-final", methods=["GET"])
def final_exam_students_excel():
    try:
        subject_id = request.args.get('idMateria')

        final_exam_students = requests.get(
            f'http://admin:8081/api/mesas-examen/{subject_id}/estudiantes').json()

        subject = requests.get(
            f'http://admin:8081/api/materias/{subject_id}').json()

        encoded_excel = final_exam_students_excel_generator(
            subject, final_exam_students)

        return Response(encoded_excel, status=200, mimetype='application/json')

    except Exception as e:
        return Response(json.dumps({"error": str(e)}), status=500, mimetype='application/json')


@app.route("/excel/inscriptos-final-notas", methods=["GET"])
def final_exam_students_qualifications_excel():
    try:
        final_exam_id = request.args.get('idMesaExamen')

        final_exam = requests.get(
            f'http://admin:8081/api/mesas-examen/{final_exam_id}').json()

        final_exam_students = requests.get(
            f'http://admin:8081/api/mesas-examen/{final_exam_id}/estudiantes').json()

        encoded_excel = final_exam_students_qualifications_excel_generator(
            final_exam['materia'], final_exam_students)

        return Response(encoded_excel, status=200, mimetype='application/json')

    except Exception as e:
        return Response(json.dumps({"error": str(e)}), status=500, mimetype='application/json')


@app.route("/excel/carga-inscriptos-final-notas", methods=["POST"])
def final_exam_students_qualifications_excel_reader():
    try:
        final_exam_id = request.args.get('idMesaExamen')
        subject_id = request.args.get('idMateria')
        teacher_id = request.args.get('idDocente')

        if 'file' not in request.files:
            raise Exception('Must send excel file (.xlsx)')
        file = request.files['file']
        if file.filename == '':
            raise Exception('Must select an excel file (.xlsx)')
        if file and allowed_file(file.filename):
            filename = secure_filename(file.filename)
            file.save(os.path.join(filename))
        else:
            raise Exception('File must be .xlsx')

        excel_data = final_exam_students_qualifications_excel_parser(filename)

        payload = f"""<?xml version=\"1.0\" encoding=\"utf-8\"?>
                    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                                    xmlns:us="http://www.unla.com/docente/soapDocentes">
                        <soapenv:Header/>
                        <soapenv:Body>
                            <us:SolicitudAlumnosFinal>
                                <us:idDocente>{teacher_id}</us:idDocente>
                                <us:idMateria>{subject_id}</us:idMateria>
                                <us:idMesaExamen>{final_exam_id}</us:idMesaExamen>"""

        for excel_student in excel_data:

            students_data = requests.get(
                'http://admin:8081/api/usuarios?rol=ESTUDIANTE').json()

            student_data = None

            for json_student in students_data:
                if int(json_student['dni']) == int(excel_student['dni']):
                    student_data = json_student

                    payload = payload + f"""<us:NotaAlumnoFinal>
                                            <us:id>{student_data['id']}</us:id>
                                            <us:notaExamen>{excel_student['examen_final']}</us:notaExamen>
                                        </us:NotaAlumnoFinal>"""

        payload = payload + """</us:SolicitudAlumnosFinal>
                        </soapenv:Body>
                    </soapenv:Envelope>"""

        headers = {
            'Content-Type': 'text/xml; charset=utf-8'
        }

        soap_response = requests.request(
            "POST", 'http://docente:8083/soapWS', headers=headers, data=payload)

        if soap_response.status_code == 200:
            return Response('Excel procesado correctamente.', status=200, mimetype='application/json')
        else:
            raise Exception(
                f'El Excel no fue procesado correctamente. {soap_response} - {soap_response.status_code}')

    except Exception as e:
        return Response(json.dumps({"error": str(e)}), status=500, mimetype='application/json')


if __name__ == '__main__':
    logging.basicConfig()
    app.run(debug=True)
