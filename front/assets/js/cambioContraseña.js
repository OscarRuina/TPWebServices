btn_confirmar.addEventListener('click', (e) => {
    confirmar();
    e.preventDefault();
})

async function confirmar() {
    var password = $('#password').val();
    var passwordRepeat = $('#passwordRepeat').val();
    
    if (password == passwordRepeat) {
        var id = sessionStorage.getItem('id');
        var url = URLADMIN + "api/auth/primer-login/" + id;
        
        var data = {
            'contraseña': password
        };
        console.log(data)
        
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
          window.location.replace("index.html");
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