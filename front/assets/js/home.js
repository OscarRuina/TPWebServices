$(document).ready( ()=> {
    
    const usuario = ()=>{
        var nombre = sessionStorage.getItem("nombreUsuario");
        
        data = ` <span class="d-none d-lg-inline me-2 text-gray-600 small">${nombre}</span> <i class="fa fa-user-circle-o"></i>`
        $('#nombreApellido').html(data);
        
    }
    
    usuario();
})