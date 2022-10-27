btn_agregar.addEventListener("click", (e) => {
    var url = "registroApertura.html";
    window.location.replace(url);
    e.preventDefault();
});

$(document).ready( ()=> {

    const list=()=>{
        var url = URLADMIN + "api/inscripciones";
        //console.log(url);
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
                {data: 'tipo', defaultContent: "<i> </i>"},  
                {data: 'inicio', defaultContent: "<i> </i>"},
                {data: 'fin', defaultContent: "<i> </i>"},
            ], 
        });  
    }

    list(); 
})
