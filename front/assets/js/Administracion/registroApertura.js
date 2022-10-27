btn_confirmar.addEventListener("click", (e) => {
    guardar();
    e.preventDefault();
});

function guardar() {    
    var url = URLADMIN + "api/inscripciones";
    var method = "POST";
        
    var data = {
        "tipo": $('#tipo').val(),
        "inicio": $('#inicio').val(),
        "fin": $('#fin').val(),
    };
    
    fetch(url, {
        method: method,
        body: JSON.stringify(data),
        headers: {
          "Content-Type": "application/json",
        },
    }).then(response => response.json())
    .then(data => {
        success("apertura.html")
    })
    .catch(err => error(err))
}
