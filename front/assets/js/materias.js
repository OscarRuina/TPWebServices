btn_agregar.addEventListener("click", (e) => {
    var url = "registroMateria.html";
    window.location.replace(url);
    e.preventDefault();
});

$(document).ready( ()=> {
    
    const list=()=>{
        var url = URLADMIN + "api/materias" ;
        var table = $('#dataTables').DataTable({
            "destroy": true, 
            "autoWidth": false, 
            "language": {
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
            "ajax": {
                "url": url,
                "type": "GET",
                "dataSrc": "",
            }, 
            "columns": [
                {data: 'id', defaultContent: "<i> </i>"}, 
                {data: 'nombre', defaultContent: "<i> </i>"},       
                {data: 'carrera', defaultContent: "<i> </i>"},
                {data: 'cuatrimestre', defaultContent: "<i> </i>"},
                {data: 'añoMateria', defaultContent: "<i> </i>"},
                {data: 'añoCuatrimestre', defaultContent: "<i> </i>"},
                {data: 'dia', defaultContent: "<i> </i>"},
                {data: 'turno', defaultContent: "<i> </i>"},
                {data: 'horaInicio', defaultContent: "<i> </i>"},
                {data: 'horaFinalizacion', defaultContent: "<i> </i>"}
            ],    
            "columnDefs": [
                {
                  "data": null,
                  "defaultContent": '<div class="dropdown"><a class="btn btn-sm btn-icon-only text-dark" aria-haspopup="true" aria-expanded="false" data-bs-toggle="dropdown"><i class="fas fa-ellipsis-v"></i></a><div class="dropdown-menu dropdown-menu-right dropdown-menu-arrow"><a class="dropdown-item" id="btn-edit" href="#">Editar</a><a class="dropdown-item" id="btn-delete" href="#">Borrar</a></div></div>',
                  "targets": 10
                },
            ], 
        });
    }
    
    const deleteM = () => {
        $(document).on('click','#btn-delete', function(){
            
            Swal.fire({
                title: '¿Estas seguro?',
                text: "No podras revertir los cambios",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Si, eliminar'
            }).then((result) => {
                if (result.isConfirmed) {
                    let btn = $(this)[0].parentElement.parentElement.parentElement.parentElement;
                    let id = $(btn).find("td").eq(0).html();
                    var url = URLADMIN + "api/materias/" + id;
                    fetch(url, {
                        method: 'DELETE', // or 'PUT'
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
    
    const edit = () => {
        $(document).on('click','#btn-edit', function(){
            let btn = $(this)[0].parentElement.parentElement.parentElement.parentElement;
            let id = $(btn).find("td").eq(0).html();
            var url = "registroMateria.html?id=" + id
            window.location.replace(url);
        })
    } 
    
    edit();
    deleteM();
    list(); 
})