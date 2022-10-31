$(document).ready( ()=> {
    
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
    
    listMaterias();
    
})