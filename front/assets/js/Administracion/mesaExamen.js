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
                    {data: 'materia', defaultContent: "<i> </i>"},       
                    {data: 'docente', defaultContent: "<i> </i>"},
                    {data: 'dia', defaultContent: "<i> </i>"},
                    {data: 'hora', defaultContent: "<i> </i>"},
                ],
            });
        });
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
                $('#nombre').html(data);
            }
        })
    } 
    
    const reporteDescargar = () => {
        $(document).on('click','#btn_reporte', function(){
            //"URLADMIN"+ "pdf/materias?""
            //127.0.0.1:5000/pdf/materias?cuatrimestre=PRIMERO&añoCuatrimestre=2022
            //cuatrimestre = 2
            //añoCuatrimestre = 2022
            var cuatrimestre = $('#cuatrimestre').val();
            var anioCuatrimestre = $('#anioCuatrimestre').val();
            
            var url = URLREPORT + "pdf/llamado-final";
            
            console.log(url);
            
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
                    const fileName = "listado_de_materias_por_cuatrimestre.pdf";
                    downloadLink.href = linkSource;
                    downloadLink.download = fileName;
                    downloadLink.click();
                 }))
                .catch(err => error(err))
            }) 
    }
    
    reporteDescargar();
    listMaterias();
    list(); 
})