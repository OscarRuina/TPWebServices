$(document).ready( ()=> {
    const validarFechas=()=>{
        var url = URLADMIN + "api/inscripciones";
        const tiempoTranscurrido = Date.now();
        const hoy = new Date(tiempoTranscurrido);
        var fechaAhora = hoy;
        
        fetch(url, {
            method: 'GET',
            headers: {
                "Content-Type": "application/json"
            }
        }).then(response => response.json()
            .then(data => {
                //console.log(data);
            //[{"id":1,"inicio":"2022-11-03","fin":"2022-11-29","tipo":"Finales"},{"id":2,"inicio":"2022-11-01","fin":"2022-11-30","tipo":"Cursada"}]
                
                data.forEach(element => {
                    var inicio = Date.parse(element.inicio);
                    var fin = Date.parse(element.fin);

                    if (element.tipo === "Finales"){
                        if (fechaAhora >= inicio && fechaAhora <= fin){
                            $('#InscripcionExamenes').attr('href', "InscripcionExamenes.html");
                        } else {
                            $('#InscripcionExamenes').attr('href', "AccesoDenegado.html");
                        }
                    }
                    if (element.tipo === "Cursada"){
                        if (fechaAhora >= inicio && fechaAhora <= fin){
                            $('#InscripcionMaterias').attr('href', "InscripcionMaterias.html");
                        }else{
                            $('#InscripcionMaterias').attr('href', "AccesoDenegado.html");
                        }
                    }
                });
            
            })).catch(err => error(err))
    }
                  
    validarFechas();
})