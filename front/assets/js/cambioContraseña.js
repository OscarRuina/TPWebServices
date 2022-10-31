btn_confirmar.addEventListener('click', (e) => {
    confirmar();
    e.preventDefault();
})

async function confirmar() {
    var password = $('#password').val();
    var passwordRepeat = $('#passwordRepeat').val();
    var valores = getGET();
    var rol = valores['rol'];
    
    if (password == passwordRepeat) {
        var id = sessionStorage.getItem('id');
        var url = URLADMIN + "api/auth/primer-login/" + id;
        
        var data = {
            'contraseña': password
        };
        
        var response = fetch(url, {
            method: 'PUT',
            body: JSON.stringify(data),
            headers: {
                'Content-Type': 'application/json'
            }
        });
        
        Swal.fire({
              position: 'center',
              icon: 'success',
              title: 'Contraseña Modificada Correctamente',
              showConfirmButton: false,
              timer: 1000
        }).then( function() {
            if (rol == "ROLE_ADMIN"){
                window.location.replace("./Administrador/home.html");
            }
            if (rol == "ROLE_ESTUDIANTE"){
                window.location.replace("./Estudiante/homeEstudiante.html");
            }
            if (rol == "ROLE_DOCENTE"){
                window.location.replace("./Docente/homeDocente.html");
            }
        });

    } else {
        Swal.fire({
          position: 'center',
          icon: 'error',
          title: 'Contraseña distintas.',
          showConfirmButton: false,
          timer: 1000
        })
    }
}