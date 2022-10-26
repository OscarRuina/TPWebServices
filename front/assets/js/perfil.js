btn_confirmar.addEventListener("click", (e) => {
    guardar();
    e.preventDefault();
});
/*
function createCORSRequest(method, url) {
    var xhr = new XMLHttpRequest();
    if ("withCredentials" in xhr) {
        xhr.open(method, url, false);
    } else if (typeof XDomainRequest != "undefined") {
        alert
        xhr = new XDomainRequest();
        xhr.open(method, url);
    } else {
        console.log("CORS not supported");
        alert("CORS not supported");
        xhr = null;
    }
    return xhr;
}

function guardar() {
    var url = URLESTUDIANTE + "soapWS";
    var id = sessionStorage.getItem("id");
    var mail = $('#mail').val()
	var password = $('#password').val()
    
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open('POST', url, true);

    // build SOAP request
    var dataHeader = '<?xml version="1.0" encoding="utf-8"?>'+
        '<soapenv:Envelope '+
        'xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:us="http://www.unla.com/estudiante/soapEstudiantes">'+
        '<soapenv:Header/>';
    
    var dataBody = 
        '<soapenv:Body>'+
            '<us:SolicitudModificacion>'+
                '<us:id>' + id + '</us:id>'+
                '<us:email>' + mail + '</us:email>'+
                '<us:password>' + password + '</us:password>'+
            '</us:SolicitudModificacion>'+
        '</soapenv:Body>';
    
    var dataEnd = '</soapenv:Envelope>';
    
	var data = dataHeader + dataBody + dataEnd;
    const beautifiedXmlText = new XmlBeautify().beautify(data);
    console.log(beautifiedXmlText);

    var str = beautifiedXmlText;
    
    var xhr = createCORSRequest("POST", "http://localhost:8082/soapWS");
    if(!xhr){
     console.log("XHR problema");
     //return;
    }

    xhr.onload = function (){
     var results = xhr.responseText;
     console.log(results);
    }

    xhr.setRequestHeader('Content-Type', 'text/xml');
    xhr.send(str);
}*/


function guardar() {    
    var url = URLESTUDIANTE + "soapWS";
    console.log(url);
    var id = sessionStorage.getItem("id");
    var mail = $('#mail').val()
	var password = $('#password').val()
    
    var dataHeader = '<?xml version="1.0" encoding="utf-8"?>'+
        '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:us="http://www.unla.com/estudiante/soapEstudiantes"> <soapenv:Header/>';
    
    var dataBody = 
        '<soapenv:Body>'+
            '<us:SolicitudModificacion>'+
                '<us:id>' + id + '</us:id>'+
                '<us:email>' + mail + '</us:email>'+
                '<us:password>' + password + '</us:password>'+
            '</us:SolicitudModificacion>'+
        '</soapenv:Body>';
    
    var dataEnd = '</soapenv:Envelope>';
    
	var data = dataHeader + dataBody + dataEnd;
    const beautifiedXmlText = new XmlBeautify().beautify(data);
    
    var codigo = new DOMParser();
    var oDOM = codigo.parseFromString(beautifiedXmlText, "text/xml");
    console.log(oDOM.documentElement);
    
    fetch(url, {
        method: 'POST',
        body: oDOM.documentElement,
        headers: {
            "Content-Type": "text/xml",
            "Origin": "http://localhost:5500" 
        },
        
    }).then(response => response.text()
        .then(data => {
            console.log(data);
            //success("indexEstudiante.html")
    }))
    .catch(err => console.error(err))
}