from flask import Flask, request, Response
from flask_cors import CORS
from pdf_generator import subjects_by_quarter_pdf_generator
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
        quarter_year = request.args.get('añoCuatrimestre')
        quarter_subjects = []

        for subject in subject_data:
            if quarter in str(subject['cuatrimestre']) and quarter_year in str(subject['añoCuatrimestre']):
                quarter_subjects.append(subject)

        encoded_pdf = subjects_by_quarter_pdf_generator(
            quarter, quarter_year, quarter_subjects)
        return Response(encoded_pdf, status=200, mimetype='application/json')

    except Exception as e:
        return Response(json.dumps({"error": str(e)}), status=500, mimetype='application/json')


if __name__ == '__main__':
    logging.basicConfig()
    app.run(debug=True)
