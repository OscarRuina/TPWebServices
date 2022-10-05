btn_confirmar.addEventListener("click", (e) => {
    guardarUsuario();
    e.preventDefault();
});

async function guardarUsuario() {    
    //Agregar, validar que no exista ese mail de usuario.
    var url = "";

    const data = {
        "nombre": $('#nombre').val(),
        "apellido": $('#apellido').val(),
        "dni": $('#dni').val(),
        "email": $('#mail').val(),
        "carrera": $('#carrera').val(),
        "rol": $('#roles').val(),
    };
    
    var valores = getGET();
    var user = valores['user'];

    if (user != null) {       
        url = URLADMIN + "api/usuarios/" + user;
        var method = "PUT";
    } else {
        url = URLADMIN + "api/usuarios";
        var method = "POST";
    }

    fetch(url, {
        method: method,
        body: JSON.stringify(data),
        headers: {
          "Content-Type": "application/json",
        },
    }).then(response => response.json())
    .then(data => success("index.html")) // Manipulate the data retrieved back
    .catch(err => error(err)) // Do something with the error
    //.then((response) => console.error("Success:", response) );

}


$(document).ready( ()=> {
     
    const detalles = () => {
        var valores = getGET();
        var user = valores['user'];
        if (user != null) {
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
    
    detalles();
})