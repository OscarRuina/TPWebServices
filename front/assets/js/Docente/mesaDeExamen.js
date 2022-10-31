var urlGlobal;

$(document).ready( ()=> {
    
    const list=()=>{
        var idDocente = sessionStorage.getItem("id");
        var valores = getGET();
        var idMateria = valores['idMateria']
        var url = URLADMIN + 'api/mesas-examen/materia/' + idMateria;
        
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
                        {"data": 'materia', defaultContent: "<i> </i>"},       
                        {"data": 'docente', defaultContent: "<i> </i>"},
                        {"data": 'dia', defaultContent: "<i> </i>"},
                        {"data": 'hora', defaultContent: "<i> </i>"},
                        {"data": 'activo', defaultContent: "<i> </i>"},
                    ],
                    "columnDefs": [
                    {
                      "data": null,
                      "defaultContent": '<div class="dropdown"><a class="btn btn-sm btn-icon-only text-dark" aria-haspopup="true" aria-expanded="false" data-bs-toggle="dropdown"><i class="fas fa-ellipsis-v"></i></a><div class="dropdown-menu dropdown-menu-right dropdown-menu-arrow">'+
                      '<a class="dropdown-item" id="btn-alumnos" href="#">Alumnos inscriptos</a>'+
                      '<a class="dropdown-item" id="btn-descargar" href="#">Descargar Excel</a>'+
                      '<a class="dropdown-item" id="btn-enviar" href="#">Enviar Excel</a>'+
                      '</div></div>',
                      "targets": 6
                    },
            ], 
                });
            }))
            .catch(err => error(err))
    }
    
    const llamarModalNotasCursadas=()=>{
        $(document).on('click','#btn-enviar', function(){
            var idDocente = sessionStorage.getItem("id");
            
            var valores = getGET();
            var idMateria = valores['idMateria']
            
            let btn = $(this)[0].parentElement.parentElement.parentElement.parentElement;
            let idMesa = $(btn).find("td").eq(0).html();
            
            //http://localhost:5000/excel/carga-iscriptos-final-notas?idMateria=1&idMesaExamen=1&idDocente=1
            //http://localhost:5000/excel/carga-iscriptos-final-notas?idMateria=1&idMesaExamen=1&idDocente=3
            urlGlobal = URLREPORT + 'excel/carga-inscriptos-final-notas?idMateria=' + idMateria + '&idMesaExamen='+ idMesa + '&idDocente=' + idDocente ;
            console.log(urlGlobal);
            
            $("#enviarCursada").modal("show");
        })
    }     
    
    const descargarExcel=()=>{
        $(document).on('click','#btn-descargar', function(){
            let btn = $(this)[0].parentElement.parentElement.parentElement.parentElement;
            let id = $(btn).find("td").eq(0).html();
            
            //http://localhost:5000/excel/inscriptos-final-notas?idMesaExamen=1
            var url = URLREPORT + 'excel/inscriptos-final-notas?idMesaExamen=' + id;
            
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
                    const fileName = "Mesa_examen_notas.xlsx";
                    downloadLink.href = linkSource;
                    downloadLink.download = fileName;
                    downloadLink.click();
                 }))
                .catch(err => error(err))
        })
    }     

    const enviarExcelNotasCursadas=()=>{
        $(document).on('click','#btn_enviar_notas_cursada', function(){
            const input = document.getElementById('fileMesa');
            var formdata = new FormData();
            formdata.append('file', input.files[0])

            //console.log(formdata)
            
            var requestOptions = {
              method: 'POST',
              body: formdata //file
            };

            fetch(urlGlobal, requestOptions)
              .then(response => response.text())
              .then(data => {
                    var URLactual2 = window.location.href;
                    success(URLactual2);
                    $("#enviarCursada").modal("hide");
                 })
              .catch(error => error(err)); 
        })
    }     
    
    const alumnosPorMesaExamen=()=>{
        $(document).on('click','#btn-alumnos', function(){
            var Valores = getGET();
            var idMateria = parseInt(Valores['idMateria']);
            
            var idDocente = sessionStorage.getItem("id");
            let btn = $(this)[0].parentElement.parentElement.parentElement.parentElement;
            let id = $(btn).find("td").eq(0).html();
            var url = "alumnosFinal.html?idMesa=" + id + "&idMateria="+idMateria;
            window.location.replace(url);
        })
    }
         
    
         
    list(); 
    llamarModalNotasCursadas();
    descargarExcel();
    enviarExcelNotasCursadas();
    alumnosPorMesaExamen();
})