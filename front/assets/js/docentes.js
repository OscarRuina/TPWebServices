var idGlobal;

btn_agregar.addEventListener("click", (e) => {
    var url = "registroDocente.html?rol=" + "DOCENTE"
    window.location.replace(url);
    e.preventDefault();
});

$(document).ready( ()=> {

    const list=()=>{
            var rol = "DOCENTE";
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
                      "defaultContent": '<div class="dropdown"><a class="btn btn-sm btn-icon-only text-dark" aria-haspopup="true" aria-expanded="false" data-bs-toggle="dropdown"><i class="fas fa-ellipsis-v"></i></a><div class="dropdown-menu dropdown-menu-right dropdown-menu-arrow"><a class="dropdown-item" id="btn-edit" href="#">Editar</a><a class="dropdown-item" id="btn-delete" href="#">Borrar</a><a class="dropdown-item" id="btn-list" href="#">Listado Materias</a><a class="dropdown-item" id="btn-asig" href="#">Asignar Materias</a></div></div>',
                      "targets": 8
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
                    let btn = $(this)[0].parentElement.parentElement.parentElement.parentElement;
                    let id = $(btn).find("td").eq(0).html();
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
            let btn = $(this)[0].parentElement.parentElement.parentElement.parentElement;
            let id = $(btn).find("td").eq(0).html();
            var url = "registroDocente.html?user=" + id + "&rol=DOCENTE"
            window.location.replace(url);
        })
    } 
    
    const listMateriasDocente = () => {
        $(document).on('click','#btn-list', function(){
            let btn = $(this)[0].parentElement.parentElement.parentElement.parentElement;
            let id = $(btn).find("td").eq(0).html();
            var url = "materiasDocente.html?user=" + id
            window.location.replace(url);
        })
    } 
    
    const listMaterias = () => {
        var url = URLADMIN + "api/materias"
        $.ajax({
            url: url,
            type: 'GET',
            dataType: 'json',
            success: function(res){
                let data = '';
                res.forEach(element => {
                    data+=`
                    <option value=${element.id}>${element.nombre}</option>
                    `
                });
                $('#materias').html(data);
            }
        })
    } 
    
    const asigMaterias = () => {
        $(document).on('click','#btn-asig', function(){
            let btn = $(this)[0].parentElement.parentElement.parentElement.parentElement;
            idGlobal = $(btn).find("td").eq(0).html();
            $("#myModal").modal("show");
        }) 
    }
    
    const guardarMateriaAsignada = () => {
        $(document).on('click','#btn_guardarMateriaAsignada', function(){
            var url = URLADMIN + "api/usuarios/" + idGlobal + "/materias";
            //console.log(url);
            var method = "PUT";
            var data = {
                "idMateria": $('#materias').val(),
            };
            fetch(url, {
                method: method,
                body: JSON.stringify(data),
                headers: {
                  "Content-Type": "application/json",
                },
            }).then(res => res.text()
                .then(data => {
                    //console.log(data);
                    success("docentes.html")
                }))
            .catch(err => error(err))
        }) 
    }
    
    listMateriasDocente();
    asigMaterias();
    listMaterias();
    guardarMateriaAsignada();
    deleteUsuario();
    editUsuario();
    list(); 
})
