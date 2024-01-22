
package com.example.bazar.Controller;

import com.example.bazar.Entities.Cliente;
import com.example.bazar.Services.IclienteService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class clienteController {
    @Autowired
    IclienteService clienServ;
    
    @PostMapping("/clientes/crear")
    public void agregarCliente(@RequestBody Cliente cli){
        clienServ.agregarClienteService(cli);
    }
    
    @GetMapping("/clientes/{id}")
    public Cliente buscarCliente(@PathVariable Long id){
        return clienServ.buscarClienteService(id);
    }
    
    @GetMapping("/clientes")
    public List<Cliente> listaClientes(){
        return clienServ.listaClientesService();
    }
    
    @PutMapping("/clientes/editar/{id}")
    public Cliente editarCliente(@PathVariable Long id,
                                 @RequestParam (required = false, name = "nombre") String nuevo_nombre,
                                 @RequestParam (required = false, name = "apellido") String nuevo_apellido,
                                 @RequestParam (required = false, name = "dni") String nuevo_dni){
        
        clienServ.editarClienteService(id, nuevo_nombre, nuevo_apellido, nuevo_dni);
        return buscarCliente(id);
    }
    
    @DeleteMapping("/clientes/eliminar/{id}")
    public void eliminarCliente(@PathVariable Long id){
        clienServ.eliminarClienteService(id);
    }
    
}
