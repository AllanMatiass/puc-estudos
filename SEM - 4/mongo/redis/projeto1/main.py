from flask import Flask
from service.redis_service import search_student

app = Flask(__name__)

@app.route('/search/<string:key>')
def search(key):
    student = search_student(key)
    return student if student else {}