btn_login.addEventListener('click', (e) => {
    validarUsuario();
    e.preventDefault();
})

function alerta(){
    Swal.fire({
      position: 'center',
      icon: 'error',
      title: 'Usuario o contraseña inválido.',
      showConfirmButton: false,
      timer: 1000
    })
} 

async function validarUsuario() {
    var url = URLADMIN + "api/auth/login"
    var nombreUsuario = $('#nombreUsuario').val()
    var contraseña = $('#password').val()
    
    var data = {
        'nombreUsuario': nombreUsuario,
        'contraseña': contraseña
    };
    
    fetch(url, {
            method: 'POST',
            body: JSON.stringify(data),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(res => res.text()
            .then(data => {
                const json = JSON.parse(data);
                //console.log(json);
                if (json.error) {
                   alerta()
                } else {
                    sessionStorage.setItem('id', json.id); 
                    sessionStorage.setItem('nombreUsuario', json.nombreUsuario);
                    sessionStorage.setItem('rol', json.rol);
                    if (json.primerLogin == true) {
                        url = "cambioContraseña.html?rol=" + json.rol
                        window.location.replace(url);
                    } else {
                        if (json.rol == "ROLE_ADMIN"){
                            window.location.replace("./Administrador/home.html");
                        }
                        if (json.rol == "ROLE_ESTUDIANTE"){
                            sessionStorage.setItem('carrera', json.carrera);
                            window.location.replace("./Estudiante/homeEstudiante.html");
                        }
                        if (json.rol == "ROLE_DOCENTE"){
                            sessionStorage.setItem('carrera', json.carrera);
                            window.location.replace("./Docente/homeDocente.html");
                        }
                    } 
                }  
            }))
          .catch(error => alerta());
}