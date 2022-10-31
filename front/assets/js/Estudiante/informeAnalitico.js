$(document).ready( ()=> {
    
    const list=()=>{
        var idEstudiante = sessionStorage.getItem("id");
        var url = URLESTUDIANTE;
        var dataBody = 
            '<soapenv:Body>'+
                '<us:SolicitudIdEstudiante>'+
                    '<us:id>'+idEstudiante+'</us:id>'+
                '</us:SolicitudIdEstudiante>'+
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
                //console.log(jsonObj);
                //console.log(jsonObj.Envelope.Body.Analitico.nota.length);  
            
                if (jsonObj.Envelope.Body.Analitico.nota.length){
                    var datos = jsonObj.Envelope.Body.Analitico.nota;
                }else{
                    var datos = {}
                    var array  = [];
                    array.push({ 
                        "id": jsonObj.Envelope.Body.Analitico.nota.id.toString(),
                        "materia": jsonObj.Envelope.Body.Analitico.nota.materia.toString(),
                        "notaExamen": jsonObj.Envelope.Body.Analitico.nota.notaExamen.toString(),
                        "notaFinal": jsonObj.Envelope.Body.Analitico.nota.notaFinal.toString(),
                    });
                    datos = array;
                }
                var nota = 0;
                var cantidadDeNotas = 0;
            
                datos.forEach(element => {
                    nota = nota + parseFloat(element.notaFinal);
                    cantidadDeNotas = cantidadDeNotas + 1;
                });   
            
                var promedio = nota / cantidadDeNotas;
                data = ` <span>${promedio}</span>`
                $('#nota').html(data);
            
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
                        {"data": 'materia', defaultContent: "<i> </i>"},       
                        {"data": 'notaExamen', defaultContent: "<i> </i>"},
                        {"data": 'notaFinal', defaultContent: "<i> </i>"}
                    ],
                    /*columnDefs: [
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
                    ], */
                });
            }))
            //.catch(err => error(err))
    }
    
    list(); 
})