var urlGlobal;
var idGlobal1;

$(document).ready( ()=> {
    
    const list=()=>{
        var idDocente = sessionStorage.getItem("id");
        var url = URLDOCENTE;
        var valores = getGET();
        var idMateria = valores['idMateria']
        
        var dataBody = 
            '<soapenv:Body>'+
                '<us:SolicitudAlumnosPorMateria>'+
                    '<us:idDocente>'+ idDocente +'</us:idDocente>'+
                    '<us:idMateria>'+ idMateria +'</us:idMateria>'+
                '</us:SolicitudAlumnosPorMateria>'+
            '</soapenv:Body>';

        var data = dataHeaderDocente + dataBody + dataEnd;
        const beautifiedXmlText = new XmlBeautify().beautify(data);
        //console.log(beautifiedXmlText);
        
        fetch(url, {
            method: 'POST',
            body: beautifiedXmlText,
            headers: {
                "Content-Type": "text/xml"
            }
        }).then(response => response.text()
            .then(data => {
                var respuesta = new XmlBeautify().beautify(data);
                var x2js = new X2JS();
                var jsonObj = x2js.xml_str2json( respuesta );
            
                console.log(jsonObj.Envelope.Body.RespuestaAlumnosPorMateria.Alumno);
                //console.log(jsonObj.Envelope.Body.Materias.item.length);  
            
                if (jsonObj.Envelope.Body.RespuestaAlumnosPorMateria.Alumno.length){
                    var datos = jsonObj.Envelope.Body.RespuestaAlumnosPorMateria.Alumno;
                }else{
                    var datos = {}
                    var array  = [];
                    array.push({ 
                        "id": jsonObj.Envelope.Body.RespuestaAlumnosPorMateria.Alumno.id.toString(),
                        "nombre": jsonObj.Envelope.Body.RespuestaAlumnosPorMateria.Alumno.nombre.toString(),
                        "apellido": jsonObj.Envelope.Body.RespuestaAlumnosPorMateria.Alumno.apellido.toString(),
                        "dni": jsonObj.Envelope.Body.RespuestaAlumnosPorMateria.Alumno.dni.toString(),
                        "email": jsonObj.Envelope.Body.RespuestaAlumnosPorMateria.Alumno.email.toString()
                    });
                    datos = array;
                }
                
                //console.log(datos);
                var table = $('#dataTables').DataTable({
                    destroy: true, 
                    autoWidth: false, 
                    language: {
                        "decimal": "",
                        "emptyTable": "No hay información",
                        "info": "Mostrando _START_ a _END_ de _TOTAL_ Entradas",
                        "infoEmpty": "Mostrando 0 to 0 of 0 Entradas",
                        "infoFiltered": "(Filtrado de _MAX_ total entradas)",
                        "infoPostFix": "",
                        "thousands": ",",
                        "lengthMenu": "Mostrar _MENU_ Entradas",
                        "loadingRecords": "Cargando...",
                        "processing": "Procesando...",
                        "search": "Buscar:",
                        "zeroRecords": "Sin resultados encontrados",
                        "paginate": {
                            "first": "Primero",
                            "last": "Ultimo",
                            "next": "Siguiente",
                            "previous": "Anterior"
                        }
                    },
                    data: datos,
                    columns: [
                        {"data": 'id', defaultContent: "<i> </i>"}, 
                        {"data": 'nombre', defaultContent: "<i> </i>"},       
                        {"data": 'apellido', defaultContent: "<i> </i>"},
                        {"data": 'dni', defaultContent: "<i> </i>"},
                        {"data": 'email', defaultContent: "<i> </i>"},
                    ],
                    "columnDefs": [
                        {
                          "data": null,
                          "defaultContent": '<div class="dropdown"><a class="btn btn-sm btn-icon-only text-dark" aria-haspopup="true" aria-expanded="false" data-bs-toggle="dropdown"><i class="fas fa-ellipsis-v"></i></a><div class="dropdown-menu dropdown-menu-right dropdown-menu-arrow">'+
                          '<a class="dropdown-item" id="btn_alumnos" href="#">Ingresar Notas</a>'+ 
                          '</div></div>',
                          "targets": 5
                        },
                    ], 
                });
            }))
            //.catch(err => error(err))
    }
    
    const ingresoNotasCursada =()=>{
        $(document).on('click','#btn_alumnos', function(){
            let btn = $(this)[0].parentElement.parentElement.parentElement.parentElement;
            var idEstudiante = $(btn).find("td").eq(0).html();
            idGlobal1 = idEstudiante;

            $("#modal1").modal("show");
        })
    }
    
    const enviarNotas =()=>{
        $(document).on('click','#btn_enviar', function(){
            var idDocente = sessionStorage.getItem("id");
            var Valores = getGET();
            var idMateria = parseInt(Valores['idMateria'])
            var dataBody = 
                '<soapenv:Body>'+
                    '<us:SolicitudAlumnosCursada>'+
                        '<us:idDocente>'+idDocente+'</us:idDocente>'+
                        '<us:idMateria>'+idMateria+'</us:idMateria>'+
                        '<us:NotaAlumnoCursada>'+
                            '<us:id>'+idGlobal1+'</us:id>'+
                            '<us:notaParcial1>'+ $('#nota1').val() +'</us:notaParcial1>'+
                            '<us:notaParcial2>'+ $('#nota2').val() +'</us:notaParcial2>'+
                        '</us:NotaAlumnoCursada>'+
                    '</us:SolicitudAlumnosCursada>'+
                '</soapenv:Body>';

            var data = dataHeaderDocente + dataBody + dataEnd;
            const beautifiedXmlText = new XmlBeautify().beautify(data);
            console.log(beautifiedXmlText);

            var myHeaders = new Headers();
            myHeaders.append("Content-Type", "text/xml");

            var requestOptions = {
              method: 'POST',
              headers: myHeaders,
              body: beautifiedXmlText
            };

            fetch(URLDOCENTE, requestOptions)
              .then(response => response.text())
              .then(data => {
                    var URLactual2 = window.location;
                    success(URLactual2); 
                    $("#modal1").modal("hide");
                 })
              .catch(error => error(err)); 
        })
    }
    
    list(); 
    ingresoNotasCursada();
    enviarNotas();
})