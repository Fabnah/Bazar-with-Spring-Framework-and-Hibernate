package com.example.bazar.Services;

import com.example.bazar.Entities.Producto;
import com.example.bazar.Repositories.IproductoRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class productoService implements IproductoService {

    @Autowired
    IproductoRepository repo;

    @Override
    public void agregarProducto(Producto prod) {
        repo.save(prod);
    }

    @Override
    public Producto buscarProducto(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public List<Producto> listaProductos() {
        return repo.findAll();
    }

    @Override
    public void editarProducto(Long id_original, String nuevo_nombre, String nueva_marca, Double nuevo_costo, Double nueva_cant_disponible) {
        Producto prod = buscarProducto(id_original);
        prod.setNombre(nuevo_nombre);
        prod.setMarca(nueva_marca);
        prod.setCosto(nuevo_costo);
        prod.setCant_disponible(nueva_cant_disponible);
        this.agregarProducto(prod);
    }

    @Override
    public void eliminarProducto(Long id) {
        repo.deleteById(id);
    }

    @Override
    public void descontarProducto(Long id) {
        Producto prod = buscarProducto(id);
        if (prod.getCant_disponible() > 0) {
            prod.setCant_disponible(prod.getCant_disponible() - 1);
            agregarProducto(prod);
        } else {
            throw new Error("No hay mas productos");
        }

    }

    @Override
    public List<Producto> productosFaltantes() {
        List<Producto> faltantes = new ArrayList<>();
        for (Producto producto : repo.findAll()) {
            if (producto.getCant_disponible()<=5) {
                faltantes.add(producto);
            }
        }
        return faltantes;
    }

    

}
