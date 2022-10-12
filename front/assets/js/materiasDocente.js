$(document).ready( ()=> {

    const list=()=>{
        //http://localhost:8081/api/usuarios/%id%/materias
        var valores = getGET();
        if (valores['user'] != null) {
            var id = valores['user'];
            var url = URLADMIN + "api/usuarios/" + id + "/materias";
            console.log(url);
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
                    {data: 'nombreMateria', defaultContent: "<i> </i>"},  
                    {data: 'docente', defaultContent: "<i> </i>"},
                ], 
            });
         }   
    }

    list(); 
})
