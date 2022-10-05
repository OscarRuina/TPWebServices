btn_agregar.addEventListener("click", (e) => {
    var url = "registroMesaExamen.html"
    window.location.replace(url);
    e.preventDefault();
});

$(document).ready( ()=> {

    const list=()=>{
        $(document).on('click','#btn_filtro', function(){
            var idMateria = $('#nombre').val();
            var url = URLADMIN + "api/mesas-examen/materia/" + idMateria;
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
                    {data: 'materia', defaultContent: "<i> </i>"},       
                    {data: 'docente', defaultContent: "<i> </i>"},
                    {data: 'dia', defaultContent: "<i> </i>"},
                    {data: 'hora', defaultContent: "<i> </i>"},
                ],
            });
            //setInterval( function () {
            //    table.ajax.reload(null, false);
            //}, 1000 );
        });
    }

    list(); 
})