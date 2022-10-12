btn_confirmar.addEventListener("click", (e) => {
    guardarUsuario();
    e.preventDefault();
});

async function guardarUsuario() {    
    //Agregar, validar que no exista ese mail de usuario.
    var url = "";
    var valores = getGET();
    
    if (valores['user'] != null) {    
        var user = valores['user'];
        url = URLADMIN + "api/usuarios/" + user;
        var method = "PUT";
    } else {
        url = URLADMIN + "api/usuarios";
        var method = "POST";
    }
        
    var data = {
        "nombre": $('#nombre').val(),
        "apellido": $('#apellido').val(),
        "dni": $('#dni').val(),
        "email": $('#mail').val(),
        "carrera": $('#carrera').val(),
        "rol": valores['rol'],
    };
    
    fetch(url, {
        method: method,
        body: JSON.stringify(data),
        headers: {
          "Content-Type": "application/json",
        },
    }).then(response => response.json())
    .then(data => {
        if (valores['rol'] == "DOCENTE"){
            success("docentes.html")
        } else {
            success("estudiantes.html")
        }
    })
    .catch(err => error(err))
}


$(document).ready( ()=> {
     
    const detalles = () => {
        var valores = getGET();
        $('#roles').val(valores['rol']);
        
        if (valores['user'] != null) {
            var user = valores['user'];
            $.ajax({
                url: URLADMIN + 'api/usuarios/' + user,
                type: 'GET',
                dataType: 'json',
                success: function(res){
                    $('#nombre').val(res.nombre);
                    $('#apellido').val(res.apellido);
                    $('#dni').val(res.dni);
                    $('#mail').val(res.email);
                    $('#carrera').val(res.carrera);
                    $('#roles').val(res.rol.replace("ROLE_",""));
                }
            })
        }
    }
    
    const listCarreras = () => {
        var url = URLADMIN + "api/carreras"
        $.ajax({
            url: url,
            type: 'GET',
            dataType: 'json',
            success: function(res){
                let data = '';
                res.forEach(element => {
                    data+=`
                    <option value=${element.dato}>${element.dato}</option>
                    `
                });
                $('#carrera').html(data);
            }
        })
    } 
    
    listCarreras();
    detalles();
})