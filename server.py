from flask import Flask, request
import requests

app = Flask(__name__)

@app.route("/", methods=['POST'])
def root():
    text = request.form['text'].encode('utf-8')
    dct = request.form['dct']
    annotador_response = requests.post(
        'http://localhost:8080/annotate/temporal',
        headers={
            'accept': 'text/plain', 
            'Content-Type': 'text/plain'
        },
        params={
            "dct": dct,
            "domain": "Standard",
            "language": "es",
            "format": "JSON"
        },
        data=text).json()

    return annotador_response
