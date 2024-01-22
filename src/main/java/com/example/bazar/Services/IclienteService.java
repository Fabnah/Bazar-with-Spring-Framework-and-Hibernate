
package com.example.bazar.Services;

import com.example.bazar.Entities.Cliente;
import java.util.List;


public interface IclienteService {
    
    
    public void agregarClienteService(Cliente cliente);
    
    public Cliente buscarClienteService(Long id);
    
    public List<Cliente> listaClientesService();
    
    public void editarClienteService(Long id,
                                     String nombre,
                                     String apellido,
                                     String dni);
    
    public void eliminarClienteService(Long id);
}
