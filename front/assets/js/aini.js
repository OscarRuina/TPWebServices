var URLADMIN = "http://localhost:8081/";
var login = sessionStorage.getItem("nombreUsuario")

if (login === null || login == "null"){
    var URLactual = window.location.pathname;
    if (URLactual  != "/login.html") {
		window.location.replace("login.html");
	}
}

btn_salir.addEventListener('click', (e) => {
    salir();
    e.preventDefault();
})

btn_perfil.addEventListener('click', (e) => {
    //perfil();
    e.preventDefault();
})

function getGET() {
    // capturamos la url
    var loc = document.location.href;
    // si existe el interrogante
    if(loc.indexOf('?')>0)
    {
        // cogemos la parte de la url que hay despues del interrogante
        var getString = loc.split('?')[1];
        // obtenemos un array con cada clave=valor
        var GET = getString.split('&');
        var get = {};
        // recorremos todo el array de valores
        for(var i = 0, l = GET.length; i < l; i++){
            var tmp = GET[i].split('=');
            get[tmp[0]] = unescape(decodeURI(tmp[1]));
        }
        return get;
    }
}

async function salir() {
    sessionStorage.removeItem('id');
    sessionStorage.removeItem('nombreUsuario');
    window.location.replace("index.html");
}

async function success(Ubicacion){
    Swal.fire({
        position: 'center',
        icon: 'success',
        title: 'Se a completado la operación',
        showConfirmButton: false,
        timer: 1500
    }).then( function() {
      window.location.replace(Ubicacion);
    });
} 

async function error(err){
    Swal.fire({
        position: 'center',
        icon: 'error',
        title: 'Ocurrio un error, verificar la consola',
        showConfirmButton: false,
        timer: 1500
    }).then( function() {
      console.error('Error:', err)
    });
} 