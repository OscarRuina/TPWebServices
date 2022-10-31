$(document).ready( ()=> {
    
    const list=()=>{
        var url = URLESTUDIANTE;
        var dataBody = 
            '<soapenv:Body>'+
                '<us:SolicitudMesasExamen>'+
                    '<us:activo>true</us:activo>'+
                '</us:SolicitudMesasExamen>'+
            '</soapenv:Body>';

        var data = dataHeader + dataBody + dataEnd;
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
                //console.log(jsonObj.Envelope.Body.MesasExamen.item);
            
                //console.log(jsonObj.Envelope.Body.MesasExamen.item.length);  

                if (typeof jsonObj.Envelope.Body.MesasExamen.item.length !== "undefined"){
                    var datos = jsonObj.Envelope.Body.MesasExamen.item;
                } else {
                    var datos = {}
                    var array  = [];
                    array.push({ 
                        "id": jsonObj.Envelope.Body.MesasExamen.item.id.toString(),
                        "nombre": jsonObj.Envelope.Body.MesasExamen.item.nombre.toString(),
                        "docente": jsonObj.Envelope.Body.MesasExamen.item.docente.toString(),
                        "dia": jsonObj.Envelope.Body.MesasExamen.item.dia.toString(),
                        "hora": jsonObj.Envelope.Body.MesasExamen.item.hora.toString() 
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
                        {data: 'id'}, 
                        {data: 'nombre'},       
                        {data: 'docente'},
                        {data: 'dia'},
                        {data: 'hora'},
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
                    '<us:SolicitudInscripcionMesaExamenEstudiante>'+
                        '<us:idEstudiante>'+idEstudiante+'</us:idEstudiante>'+
                        '<us:idMesaExamen>'+id+'</us:idMesaExamen>'+
                    '</us:SolicitudInscripcionMesaExamenEstudiante>'+
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
            }).then((response) => {
                Swal.fire({
                    position: 'center',
                    icon: 'success',
                    title: 'Se realizo la operación',
                    showConfirmButton: false,
                    timer: 1000
                }).then( function() {
                      list();
                });
            }).catch(err => {
                error(err)
            })
        
        }) 
    }
    
    const bajarse=()=>{
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
                            '<us:SolicitudBajaInscripcionMesaExamenEstudiante>'+
                                '<us:idEstudiante>'+idEstudiante+'</us:idEstudiante>'+
                                '<us:idMesaExamen>'+id+'</us:idMesaExamen>'+
                            '</us:SolicitudBajaInscripcionMesaExamenEstudiante>'+
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
                    }).then((response) => {
                        Swal.fire({
                            position: 'center',
                            icon: 'success',
                            title: 'Se realizo la operación',
                            showConfirmButton: false,
                            timer: 1000
                        }).then( function() {
                              list();
                        });
                    }).catch(err => {
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