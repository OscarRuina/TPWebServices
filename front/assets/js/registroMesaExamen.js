btn_confirmar.addEventListener("click", (e) => {
    guardar();
    e.preventDefault();
});

function guardar() {    
    var url = URLADMIN + "api/mesas-examen";
    var method = "POST";
        
    var data = {
        "idMateria": $('#materia').val(),
        "dia": $('#dia').val(),
        "hora": $('#hora').val(),
    };
    
    fetch(url, {
        method: method,
        body: JSON.stringify(data),
        headers: {
          "Content-Type": "application/json",
        },
    }).then(response => response.json())
    .then(data => {
        success("mesaExamen.html")
    })
    .catch(err => error(err))
}
