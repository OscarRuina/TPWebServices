var URLADMIN = "http://localhost:8081/";
var URLESTUDIANTE = "http://localhost:8082/soapWS";
var URLDOCENTE = "http://localhost:8083/soapWS";
var URLREPORT = "http://localhost:5000/";

var dataHeader = '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:us="http://www.unla.com/estudiante/soapEstudiantes"> <soapenv:Header/>';
var dataHeaderDocente = '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:us="http://www.unla.com/docente/soapDocentes"> <soapenv:Header/>';

var dataEnd = '</soapenv:Envelope>';

var loginEspecial = sessionStorage.getItem("nombreUsuario");
if (loginEspecial === null || loginEspecial == "null"){
    var URLactual = window.location.pathname;
    if (URLactual  != "/index.html") {
		window.location.replace("index.html");
	}
}

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