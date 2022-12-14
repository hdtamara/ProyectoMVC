package co.gov.mintic.tesoreria.thymeleaf.controller;

import co.gov.mintic.tesoreria.thymeleaf.entities.Rol;
import co.gov.mintic.tesoreria.thymeleaf.entities.TipoDocumento;
import co.gov.mintic.tesoreria.thymeleaf.entities.Usuario;
import co.gov.mintic.tesoreria.thymeleaf.service.IRolService;
import co.gov.mintic.tesoreria.thymeleaf.service.ITipoDocumentoService;
import co.gov.mintic.tesoreria.thymeleaf.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IRolService rolService;

    @Autowired
    private ITipoDocumentoService tipoDocumentoService;

    private final Logger LOG = Logger.getLogger(""+UsuarioController.class);

    @GetMapping("/usuarios/listar")
    public String getListUsuarios(Model model){
        LOG.log(Level.INFO,"getListUsuarios");
        List<Usuario> usuarios = usuarioService.findAll();
        for (Usuario user : usuarios)
            System.out.println(user.toString());
        model.addAttribute("usuarios", usuarios);
        return "usuarios/list";
    }

    @GetMapping("/usuarios/crear")
    public String createUsuario(Model modelo){
        LOG.log(Level.INFO,"createUsuario");
        //Usuario
        Usuario usuario = new Usuario();
        modelo.addAttribute("usuario", usuario);
        //Roles
        List<Rol> roles = rolService.findAll();
        modelo.addAttribute("roles", roles);
        // Tipo de documento
        List<TipoDocumento> tiposDocumentos = tipoDocumentoService.findAll();
        modelo.addAttribute("tiposDocumentos", tiposDocumentos);
        return "usuarios/modificar";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(Usuario user){
        LOG.log(Level.INFO,"guardarUsuario");
        user.setEstado(true);
        System.out.println(user.toString());
        user = usuarioService.createUsuario(user);
        return "redirect:/usuarios/listar";
    }

    @GetMapping("/editar/{id}")
    public String editUsuario(@PathVariable("id") long id,  Model modelo){
        LOG.log(Level.INFO,"editUsuario");
        System.out.println(id);
        Usuario usuario = usuarioService.findById(id);
        System.out.println(usuario.toString());
        //Roles
        List<Rol> roles = rolService.findAll();
        modelo.addAttribute("roles", roles);
        // Tipo de documento
        List<TipoDocumento> tiposDocumentos = tipoDocumentoService.findAll();
        modelo.addAttribute("tiposDocumentos", tiposDocumentos);
        modelo.addAttribute("usuario",usuario);
        return "usuarios/modificar";
    }

}
