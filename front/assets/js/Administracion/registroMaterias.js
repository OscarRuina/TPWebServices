btn_confirmar.addEventListener("click", (e) => {
    guardar();
    e.preventDefault();
});

async function guardar() {    
    var url = "";
    var valores = getGET();
    
    if (valores != null) {    
        var id = valores['id'];
        url = URLADMIN + "api/materias/" + id;
        var method = "PUT";
    } else {
        url = URLADMIN + "api/materias";
        var method = "POST";
    }
        
    var data = {
        "nombre": $('#materias').val(),
        "carrera": $('#carreras').val(),
        "cuatrimestre": $('#cuatrimestre').val(),
        "añoCuatrimestre": $('#anioCuatrimestre').val(),
        "añoMateria": $('#anioMateria').val(),
        "dia": $('#dia').val(),
        "horaInicio": $('#horaInicio').val(),
        "horaFinalizacion": $('#horaFinalizacion').val(),
        "turno": $('#turno').val()
    };
    
    fetch(url, {
        method: method,
        body: JSON.stringify(data),
        headers: {
          "Content-Type": "application/json",
        },
    }).then(res => res.text()
        .then(data => {
            //const json = JSON.parse(data);
            //console.log(data);
            success("materias.html")
        }))
    .catch(err => error(err))
}

$(document).ready( ()=> {
    
    const detalles = () => {
        var valores = getGET();
        
        if (valores['id'] != null) {
            var id = valores['id'];
            $.ajax({
                url: URLADMIN + 'api/materias/' + id,
                type: 'GET',
                dataType: 'json',
                success: function(res){
                    $('#materias').val(res.nombre);
                    $('#carreras').val(res.carrera);
                    $('#cuatrimestre').val(res.cuatrimestre);
                    $('#anioCuatrimestre').val(res.añoCuatrimestre);
                    $('#anioMateria').val(res.añoMateria);
                    $('#dia').val(res.dia);
                    $('#turno').val(res.turno);
                    $('#horaInicio').val(res.horaInicio);
                    $('#horaFinalizacion').val(res.horaFinalizacion);
                }
            })
        }
    }
    
    const listMaterias = () => {
        var url = URLADMIN + "api/nombres-materias"
        $.ajax({
            url: url,
            type: 'GET',
            dataType: 'json',
            success: function(res){
                let data = '';
                res.forEach(element => {
                    data+=`
                    <option value=${element.dato}>${element.dato}</option>
                    `
                });
                $('#materias').html(data);
            }
        })
    } 
    
    const listCuatrimestre = () => {
        var url = URLADMIN + "api/cuatrimestres"
        $.ajax({
            url: url,
            type: 'GET',
            dataType: 'json',
            success: function(res){
                let data = '';
                res.forEach(element => {
                    data+=`
                    <option value=${element.dato}>${element.dato}</option>
                    `
                });
                $('#cuatrimestre').html(data);
            }
        })
    } 
    
    const listCarreras = () => {
        var url = URLADMIN + "api/carreras"
        $.ajax({
            url: url,
            type: 'GET',
            dataType: 'json',
            success: function(res){
                let data = '';
                res.forEach(element => {
                    data+=`
                    <option value=${element.dato}>${element.dato}</option>
                    `
                });
                $('#carreras').html(data);
            }
        })
    } 
    
    const listDias = () => {
        var url = URLADMIN + "api/dias"
        $.ajax({
            url: url,
            type: 'GET',
            dataType: 'json',
            success: function(res){
                let data = '';
                res.forEach(element => {
                    data+=`
                    <option value=${element.dato}>${element.dato}</option>
                    `
                });
                $('#dia').html(data);
            }
        })
    } 
    
    const listTurnos = () => {
        var url = URLADMIN + "api/turnos"
        $.ajax({
            url: url,
            type: 'GET',
            dataType: 'json',
            success: function(res){
                let data = '';
                res.forEach(element => {
                    data+=`
                    <option value=${element.dato}>${element.dato}</option>
                    `
                });
                $('#turno').html(data);
            }
        })
    } 
    
    listTurnos();
    listDias();
    listCarreras();
    listCuatrimestre();
    listMaterias();
    detalles();
})