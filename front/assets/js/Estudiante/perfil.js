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
    
    var dataBody = 
        '<soapenv:Body>'+
            '<us:SolicitudModificacion>'+
                '<us:id>' + id + '</us:id>'+
                '<us:email>' + mail + '</us:email>'+
                '<us:password>' + password + '</us:password>'+
            '</us:SolicitudModificacion>'+
        '</soapenv:Body>';
    
	var data = dataHeader + dataBody + dataEnd;
    const beautifiedXmlText = new XmlBeautify().beautify(data);
    console.log(beautifiedXmlText);
    
    fetch(url, {
        method: 'POST',
        body: beautifiedXmlText,
        headers: {
            "Content-Type": "text/xml"
        }
    }).then(response => response.text()
        .then(data => {
            success("homeEstudiante.html");
    }))
    .catch(err => error(err))
}