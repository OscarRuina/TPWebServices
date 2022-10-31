
$(document).ready( ()=> {
    
    const usuario = ()=>{
        var nombre = sessionStorage.getItem("nombreUsuario");
        
        data = ` <span class="d-none d-lg-inline me-2 text-gray-600 small">${nombre}</span> <i class="fa fa-user-circle-o"></i>`
        $('#nombreApellido').html(data);
        
    }
    
    const menuPerfil = ()=>{
        var rol = sessionStorage.getItem("rol");
        var data = '';
        
        if (rol === 'ROLE_ESTUDIANTE'){
            data = '<a id="btn_perfil" class="dropdown-item" href="perfilEstudiante.html"><i class="fas fa-user fa-sm fa-fw me-2 text-gray-400"></i> Perfil</a>'+
            '<div class="dropdown-divider"></div>'+
            '<a id="btn_salir" class="dropdown-item" href="#"><i class="fas fa-sign-out-alt fa-sm fa-fw me-2 text-gray-400"></i> Salir</a> </div>';
        } else {
            data = '<a id="btn_salir" class="dropdown-item" href="#"><i class="fas fa-sign-out-alt fa-sm fa-fw me-2 text-gray-400"></i> Salir</a> </div>';
        }
        
        $('#menu').html(data);
        
    }
    
    const salir = () => {
        $(document).on('click','#btn_salir', function(){
            var rol = sessionStorage.getItem("rol");
            sessionStorage.removeItem('id');
            sessionStorage.removeItem('nombreUsuario');
            sessionStorage.removeItem('rol');
            window.location.replace("../index.html");  
        }) 
    }
    
    usuario();
    menuPerfil();
    salir();
})