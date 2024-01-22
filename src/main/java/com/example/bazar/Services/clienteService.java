
package com.example.bazar.Services;


import com.example.bazar.Entities.Cliente;
import com.example.bazar.Repositories.IclienteRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class clienteService implements IclienteService{
    @Autowired
    IclienteRepository repo;

    @Override
    public void agregarClienteService(Cliente cliente) {
        repo.save(cliente);
    }

    @Override
    public Cliente buscarClienteService(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public List<Cliente> listaClientesService() {
        return repo.findAll();
    }

    @Override
    public void editarClienteService(Long id, String nombre, String apellido, String dni) {
        Cliente cli = buscarClienteService(id);
        cli.setNombre(nombre);
        cli.setApellido(apellido);
        cli.setDni(dni);
        this.agregarClienteService(cli);
    }

    @Override
    public void eliminarClienteService(Long id) {
        repo.deleteById(id);
    }
}
