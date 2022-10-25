$(document).ready( ()=> {
    
    const reporteDescargar = () => {
        $(document).on('click','#btn_descargar_reporte', function(){
            var id = sessionStorage.getItem("id");
            
            var url = URLREPORT + "pdf/analitico?idEstudiante=" + id;
            
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
                    const fileName = "informe_analítico_materias.pdf";
                    downloadLink.href = linkSource;
                    downloadLink.download = fileName;
                    downloadLink.click();
                 }))
                .catch(err => error(err))
            }) 
    }
    
    reporteDescargar();
})