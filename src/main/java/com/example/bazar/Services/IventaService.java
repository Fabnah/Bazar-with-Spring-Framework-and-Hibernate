
package com.example.bazar.Services;

import com.example.bazar.DTO.VentaClienteDTO;
import com.example.bazar.DTO.MontoCantidadDTO;
import com.example.bazar.DTO.VentaProductoDTO;
import com.example.bazar.Entities.Cliente;
import com.example.bazar.Entities.Producto;
import com.example.bazar.Entities.Venta;
import java.time.LocalDate;
import java.util.List;


public interface IventaService {
    
    public void agregarVentaService(Venta venta);
    
    public Venta buscarVentaService(Long id);
    
    public List<Venta> listarVentasService();
    
    public void editarVentasService(Long id,
                                    LocalDate fecha_venta,
                                    Double total,
                                    List<Producto> listaProducto,
                                    Cliente cliente);
    
    public void eliminarVentaService(Long id);
    
    public VentaProductoDTO listaDeterminada(Long id);
    
    public MontoCantidadDTO balanceDiario(LocalDate fecha);
    
    public List<VentaClienteDTO> ventaMasAlta();
    
    
}
