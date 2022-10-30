var urlGlobal;
var tmppath;

$('#fileCursada').change( function(event) {
    tmppath = URL.createObjectURL(event.target.files[0]);
    console.log(tmppath);
}); 

$(document).ready( ()=> {
    
    const list=()=>{
        var idDocente = sessionStorage.getItem("id");
        var url = URLDOCENTE;
        
        var dataBody = 
            '<soapenv:Body>'+
                '<us:SolicitudMateriasAsignadas>'+
                    '<us:id>'+idDocente+'</us:id>'+
                '</us:SolicitudMateriasAsignadas>'+
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
                //console.log(jsonObj.Envelope.Body.Materias.item);
                //console.log(jsonObj.Envelope.Body.Materias.item.length);  
            
                if (jsonObj.Envelope.Body.RespuestaMateriasAsignadas.item.length){
                    var datos = jsonObj.Envelope.Body.RespuestaMateriasAsignadas.item;
                }else{
                    var datos = {}
                    var array  = [];
                    array.push({ 
                        "id": jsonObj.Envelope.Body.RespuestaMateriasAsignadas.item.id.toString(),
                        "nombre": jsonObj.Envelope.Body.RespuestaMateriasAsignadas.item.nombre.toString(),
                        "carrera": jsonObj.Envelope.Body.RespuestaMateriasAsignadas.item.carrera.toString(),
                        "añoCuatrimestre": jsonObj.Envelope.Body.RespuestaMateriasAsignadas.item.añoCuatrimestre.toString(),
                        "añoMateria": jsonObj.Envelope.Body.RespuestaMateriasAsignadas.item.añoMateria.toString(),
                        "dia": jsonObj.Envelope.Body.RespuestaMateriasAsignadas.item.dia.toString(),
                        "horaInicio": jsonObj.Envelope.Body.RespuestaMateriasAsignadas.item.horaInicio.toString(),
                        "horaFinalizacion": jsonObj.Envelope.Body.RespuestaMateriasAsignadas.item.horaFinalizacion.toString(),
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
                        {"data": 'carrera', defaultContent: "<i> </i>"},
                        {"data": 'añoCuatrimestre', defaultContent: "<i> </i>"},
                        {"data": 'añoMateria', defaultContent: "<i> </i>"},
                        {"data": 'dia', defaultContent: "<i> </i>"},
                        {"data": 'horaInicio', defaultContent: "<i> </i>"},
                        {"data": 'horaFinalizacion', defaultContent: "<i> </i>"},
                    ],
                    "columnDefs": [
                    {
                      "data": null,
                      "defaultContent": '<div class="dropdown"><a class="btn btn-sm btn-icon-only text-dark" aria-haspopup="true" aria-expanded="false" data-bs-toggle="dropdown"><i class="fas fa-ellipsis-v"></i></a><div class="dropdown-menu dropdown-menu-right dropdown-menu-arrow">'+
                      '<a class="dropdown-item" id="btn-descargar" href="#">Descargar Excel</a>'+
                      '<a class="dropdown-item" id="btn-enviar" href="#">Enviar Excel</a></div></div>',
                      "targets": 8
                    },
            ], 
                });
            }))
            .catch(err => error(err))
    }
    
    const llamarModalNotasCursadas=()=>{
        $(document).on('click','#btn-enviar', function(){
            var idDocente = sessionStorage.getItem("id");
            //http://localhost:5000/excel/carga-cursada-materia-notas?idMateria=1&idDocente=4'
            let btn = $(this)[0].parentElement.parentElement.parentElement.parentElement;
            let idMateria = $(btn).find("td").eq(0).html();
            
            urlGlobal = URLREPORT + 'excel/carga-cursada-materia-notas?idMateria=' + idMateria + '&idDocente=' + idDocente ;
            
            $("#enviarCursada").modal("show");
        })
    }     
    
    const descargarExcel=()=>{
        $(document).on('click','#btn-descargar', function(){
            //http://localhost:5000/excel/cursada-materia-notas?idMateria=''
            let btn = $(this)[0].parentElement.parentElement.parentElement.parentElement;
            let id = $(btn).find("td").eq(0).html();
            var url = URLREPORT + 'excel/cursada-materia-notas?idMateria=' + id;
            
            var method = "GET";
            fetch(url, {
                method: method,
                headers: {
                  "Content-Type": "application/json",
                },
            }).then(res => res.text()
                .then(data => {
                    const linkSource = `data:application/pdf;base64,${data}`;
                    const downloadLink = document.createElement("a");
                    const fileName = "Cursda_materias_notas.xlsx";
                    downloadLink.href = linkSource;
                    downloadLink.download = fileName;
                    downloadLink.click();
                 }))
                .catch(err => error(err))
        })
    }     
    
    //btn_enviar_notas_cursada
    const enviarExcelNotasCursadas=()=>{
        $(document).on('click','#btn_enviar_notas_cursada', function(){
            //console.log($('#fileCursada').val()); 
            
            var formdata = new FormData();
            
            //formdata.append("file", fileInput.files[0], "/C😕Users/Gonzalo/Downloads/estudiantes_inscriptos_a_final_Base de datos (2).xlsx");
            
            var mime = dataUrl.split(',')[0].split(':')[1].split(';')[0];
            console.log(typeof tmppath);
            var blob = new Blob([tmppath], { type: mime });
            
            formdata.append("file", blob);
            console.log(formdata);
            
            var requestOptions = {
              method: 'POST',
              body: formdata //file
            };

            console.log("Antes del fetch");
            fetch(urlGlobal, requestOptions)
              .then(response => response.text())
              .then(data => {
                    console.log(data);  //Respuesta
                 })
              .catch(error => error(err)); 
        })
    }     
    
    list(); 
    llamarModalNotasCursadas();
    descargarExcel();
    enviarExcelNotasCursadas();
})