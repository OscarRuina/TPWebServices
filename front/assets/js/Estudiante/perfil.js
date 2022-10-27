btn_confirmar.addEventListener("click", (e) => {
    guardar();
    e.preventDefault();
});

function guardar() {    
    var url = URLESTUDIANTE;
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
    console.log(beautifiedXmlText);
    
    //var codigo = new DOMParser();
    //var oDOM = codigo.parseFromString(beautifiedXmlText, "text/xml");
    //console.log(oDOM.documentElement);
    
    fetch(url, {
        method: 'POST',
        body: beautifiedXmlText,
        headers: {
            "Content-Type": "text/xml"
        }
    }).then(response => response.text()
        .then(data => {
            success("indexEstudiante.html");
    }))
    .catch(err => error(err))
}