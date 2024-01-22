
package com.example.bazar.Controller;

import com.example.bazar.Entities.Producto;
import com.example.bazar.Services.IproductoService;
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
public class productoController {
    @Autowired
    IproductoService producServ;
    
    @PostMapping("/productos/crear")
    public void agregarProducto(@RequestBody Producto prod){
        producServ.agregarProducto(prod);
    }
    
    @GetMapping("/productos/{id}")
    public Producto buscarProducto(@PathVariable Long id){
        return producServ.buscarProducto(id);
    }
    
    @GetMapping("/productos")
    public List<Producto> listaProductos(){
        return producServ.listaProductos();
    }
    
    @PutMapping("/productos/editar/{id}")
    public Producto editarProducto(@PathVariable Long id,
                                   @RequestParam (required = false, name = "nombre") String nuevo_nombre,
                                   @RequestParam (required = false, name = "marca") String nueva_marca,
                                   @RequestParam (required = false, name = "costo") Double nuevo_costo,
                                   @RequestParam (required = false, name = "cant_disponible") Double nueva_cant_disp){
        producServ.editarProducto(id, nuevo_nombre, nueva_marca, nuevo_costo, nueva_cant_disp);
        Producto prod = buscarProducto(id);
        return prod;
    }
    
    @DeleteMapping("/productos/eliminar/{id}")
    public void eliminarProducto(@PathVariable Long id){
        producServ.eliminarProducto(id);
    }
    
    @GetMapping("/productos/falta_stock")
    public List<Producto> faltantes(){
        return producServ.productosFaltantes();
    }
    
    
    
}
