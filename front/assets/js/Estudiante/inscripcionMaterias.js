$(document).ready( ()=> {
    
    const list=()=>{
        var carrera = sessionStorage.getItem("carrera");
        var url = URLESTUDIANTE;
        var dataBody = 
            '<soapenv:Body>'+
                '<us:SolicitudListaMaterias>' +
                    '<us:carrera>'+ carrera +'</us:carrera>'+
                '</us:SolicitudListaMaterias>'+
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
                var respuesta = new XmlBeautify().beautify(data);
                var x2js = new X2JS();
                var jsonObj = x2js.xml_str2json( respuesta );
                //console.log(jsonObj.Envelope.Body.Materias.item);
                //console.log(jsonObj.Envelope.Body.Materias.item.length);  
            
                if (jsonObj.Envelope.Body.Materias.item.length){
                    var datos = jsonObj.Envelope.Body.Materias.item;
                }else{
                    var datos = {}
                    var array  = [];
                    array.push({ 
                        "id": jsonObj.Envelope.Body.Materias.item.id.toString(),
                        "nombre": jsonObj.Envelope.Body.Materias.item.nombre.toString(),
                        "docente": jsonObj.Envelope.Body.Materias.item.docente.toString(),
                        "dia": jsonObj.Envelope.Body.Materias.item.dia.toString(),
                        "hora": jsonObj.Envelope.Body.Materias.item.hora.toString()
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
                        {"data": 'docente', defaultContent: "<i> </i>"},
                        {"data": 'dia', defaultContent: "<i> </i>"},
                        {"data": 'hora', defaultContent: "<i> </i>"}
                    ],
                    columnDefs: [
                        {
                            "data": null,
                            "defaultContent": '<a id="btn-inscribir" href="#"><i class="fa fa-check"></i></a>',
                            "targets": 5
                        },
                        {
                            "data": null,
                            "defaultContent": '<a id="btn-bajar" href="#"><i class="fa fa-trash-o"></i></a>',
                            "targets": 6
                        },
                    ], 
                });
            }))
            .catch(err => error(err))
    }
    
    const inscribirse=()=>{
          $(document).on('click','#btn-inscribir', function(){
            
            var idEstudiante = sessionStorage.getItem("id");
            let btn = $(this)[0].parentElement.parentElement;
            let id = $(btn).find("td").eq(0).html();
            var url = URLESTUDIANTE;

            var dataBody = 
                '<soapenv:Body>'+
                    '<us:SolicitudInscripcionMateriaEstudiante>'+
                        '<us:idEstudiante>'+idEstudiante+'</us:idEstudiante>'+
                        '<us:idMateria>'+id+'</us:idMateria>'+
                    '</us:SolicitudInscripcionMateriaEstudiante>'+
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
                var respuesta = new XmlBeautify().beautify(data);
                var x2js = new X2JS();
                var jsonObj = x2js.xml_str2json( respuesta );
                var verdad = jsonObj.Envelope.Body.RespuestaInscripcionMateriaEstudiante.inscripto.toString()
                
                if (verdad === "true"){
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'Se realizo la operación',
                        showConfirmButton: false,
                        timer: 1000
                    }).then( function() {
                          list();
                    });
                } 
                else {
                    Swal.fire({
                        position: 'center',
                        icon: 'warning',
                        title: 'Ya estas registrado',
                        showConfirmButton: false,
                        timer: 1000
                    }).then( function() {
                          list();
                    });
                }
                
            }))
            .catch(err => {
                error(err)
            })
        })
    }
    
    const bajarse=()=>{
        //Punto B materias
        $(document).on('click','#btn-bajar', function(){
            Swal.fire({
                title: '¿Estas seguro?',
                text: "¿Desea darse de baja?",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Si'
            }).then((result) => {
                if (result.isConfirmed) {
                    var idEstudiante = sessionStorage.getItem("id");
                    let btn = $(this)[0].parentElement.parentElement;
                    let id = $(btn).find("td").eq(0).html();
                    var url = URLESTUDIANTE;
                    
                    var dataBody = 
                        '<soapenv:Body>'+
                            '<us:SolicitudBajaInscripcionMateriaEstudiante>'+
                                '<us:idEstudiante>' + idEstudiante +'</us:idEstudiante>'+
                                '<us:idMateria>' + id + '</us:idMateria>'+
                            '</us:SolicitudBajaInscripcionMateriaEstudiante>'+
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
                        Swal.fire({
                            position: 'center',
                            icon: 'success',
                            title: 'Se realizo la operación',
                            showConfirmButton: false,
                            timer: 1000
                        }).then( function() {
                              list();
                        });
                    }))
                    .catch(err => {
                        error(err)
                    })
                }
            })
        }) 
    }
    
    list(); 
    inscribirse();
    bajarse();
})