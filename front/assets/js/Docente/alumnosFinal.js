var urlGlobal;
var idGlobal1;

$(document).ready( ()=> {
    
    const list=()=>{
        //var idDocente = sessionStorage.getItem("id");
        var valores = getGET();
        var idMesa = valores['idMesa']
        
        var url = URLADMIN + "api/mesas-examen/"+ idMesa +"/estudiantes"

        fetch(url, {
            method: 'GET',
            headers: {
                "Content-Type": "application/json"
            }
        }).then(response => response.json()
            .then(data => {
                var datos = data;
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
                        {"data": 'estudiante', defaultContent: "<i> </i>"},       
                        {"data": 'dni', defaultContent: "<i> </i>"},
                        {"data": 'email', defaultContent: "<i> </i>"},
                    ],
                    "columnDefs": [
                        {
                          "data": null,
                          "defaultContent": '<div class="dropdown"><a class="btn btn-sm btn-icon-only text-dark" aria-haspopup="true" aria-expanded="false" data-bs-toggle="dropdown"><i class="fas fa-ellipsis-v"></i></a><div class="dropdown-menu dropdown-menu-right dropdown-menu-arrow">'+
                          '<a class="dropdown-item" id="btn_alumnos" href="#">Ingresar Notas</a>'+ 
                          '</div></div>',
                          "targets": 4
                        },
                    ], 
                });
            }))
            .catch(err => error(err))
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
            var idMateria = parseInt(Valores['idMateria']);
            var idMesa = parseInt(Valores['idMesa']);

            var dataBody = 
                '<soapenv:Body>'+
                    '<us:SolicitudAlumnosFinal>'+
                        '<us:idDocente>'+idDocente+'</us:idDocente>'+
                        '<us:idMateria>'+idMateria+'</us:idMateria>'+
                        '<us:idMesaExamen>'+idMesa+'</us:idMesaExamen>'+
                        '<us:NotaAlumnoFinal>'+
                            '<us:id>'+idGlobal1+'</us:id>'+
                            '<us:notaExamen>'+ $('#nota1').val() +'</us:notaExamen>'+
                        '</us:NotaAlumnoFinal>'+
                    '</us:SolicitudAlumnosFinal>'+
                '</soapenv:Body>';
            
            var data = dataHeaderDocente + dataBody + dataEnd;
            const beautifiedXmlText = new XmlBeautify().beautify(data);
            //console.log(beautifiedXmlText);

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