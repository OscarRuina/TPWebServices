btn_agregar.addEventListener("click", (e) => {
    var url = "registroEstudiante.html?rol=" + "ESTUDIANTE"
    window.location.replace(url);
    e.preventDefault();
});

$(document).ready( ()=> {

    const list=()=>{
            var rol = "ESTUDIANTE";
            var url = URLADMIN + "api/usuarios?rol=" + rol;
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
                    {data: 'apellido', defaultContent: "<i> </i>"},
                    {data: 'dni', defaultContent: "<i> </i>"},
                    {data: 'email', defaultContent: "<i> </i>"},
                    {data: 'carrera', defaultContent: "<i> </i>"},
                    {data: 'nombreUsuario', defaultContent: "<i> </i>"},
                    {data: 'rol', defaultContent: "<i> </i>"}
                ],  //Se tienen que llamar igual que el nombre del atributo en el json    
                "columnDefs": [
                    {
                      "data": null,
                      "defaultContent": '<a id="btn-edit" href="#"><i class="fa fa-edit"></i></a>',
                      "targets": 8
                    },
                    {
                      "data": null,
                      "defaultContent": '<a id="btn-delete" href="#"><i class="fa fa-trash-o"></i></a>',
                      "targets": 9
                    },
                ], 
            });
            //setInterval( function () {
            //    table.ajax.reload(null, false);
            //}, 1000 );
    }
    
    const deleteUsuario = () => {
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
                    let btnDelete = $(this)[0].parentElement.parentElement;
                    let id = $(btnDelete).find("td").eq(0).html();
                    var url = URLADMIN + "api/usuarios/" + id;
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
    
    const editUsuario = () => {
        $(document).on('click','#btn-edit', function(){
            let btnEdit = $(this)[0].parentElement.parentElement;
            let id = $(btnEdit).find("td").eq(0).html();
            var url = "registro.html?user=" + id
            window.location.replace(url);
        })
    } 
    
    
    deleteUsuario();
    editUsuario();
    list(); 
})
