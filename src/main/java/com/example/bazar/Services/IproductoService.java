
package com.example.bazar.Services;

import com.example.bazar.Entities.Producto;
import java.util.List;


public interface IproductoService {
    
    public void agregarProducto(Producto prod);
    
    public Producto buscarProducto(Long id);
    
    public List<Producto> listaProductos();
    
    public void editarProducto(Long id_original,
                               String nuevo_nombre,
                               String nueva_marca,
                               Double nuevo_costo,
                               Double nueva_cant_disponible);
    
    public void eliminarProducto(Long id);
    
    public void descontarProducto(Long id);
    
    public List<Producto> productosFaltantes();
    
}
