import shutil
from sys import argv
from flask import Flask

dirPath = "../cdn"

app = Flask(__name__)


@app.get("/zip-project/<id>")
def zipProject(id):
    shutil.make_archive(f"{dirPath}/projects/{id}/project", 'zip', f"{dirPath}/projects/{id}/code")
    return "OK"


app.run(port=1717)
