
package com.example.bazar.Controller;

import com.example.bazar.DTO.VentaClienteDTO;
import com.example.bazar.DTO.MontoCantidadDTO;
import com.example.bazar.DTO.VentaProductoDTO;
import com.example.bazar.Entities.Cliente;
import com.example.bazar.Entities.Producto;
import com.example.bazar.Entities.Venta;
import com.example.bazar.Services.IventaService;
import java.time.LocalDate;
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
public class ventaController {
    @Autowired
    IventaService ventaServ;
    
    @PostMapping("/ventas/crear")
    public void agregarVenta(@RequestBody Venta venta){
        ventaServ.agregarVentaService(venta);
    }
    
    @GetMapping("/ventas/{id}")
    public Venta buscarVenta(@PathVariable Long id){
        return ventaServ.buscarVentaService(id);
    }
    
    @GetMapping("/ventas")
    public List<Venta> listarVentas(){
        return ventaServ.listarVentasService();
    }
    
    @PutMapping("/ventas/editar/{id}")
    public Venta editarVenta(@PathVariable Long id,
                             @RequestParam (required = false, name = "fecha_venta") LocalDate nuevaFecha,
                             @RequestParam (required = false, name = "total") Double nuevoTotal,
                             @RequestParam (required = false, name = "listaProductos") List<Producto> nuevaLista,
                             @RequestParam (required = false, name = "unCliente") Cliente nuevoCliente){
        ventaServ.editarVentasService(id, nuevaFecha, nuevoTotal, nuevaLista, nuevoCliente);
        return buscarVenta(id);
    }
    
    @DeleteMapping("/ventas/eliminar/{id}")
    public void eliminarVenta(@PathVariable Long id){
        ventaServ.eliminarVentaService(id);
    }
    
    @GetMapping("/ventas/productos/{codigo_venta}")
    public VentaProductoDTO listaVenta(@PathVariable Long codigo_venta){
        return ventaServ.listaDeterminada(codigo_venta);
    }
    
    @GetMapping("/ventas/fecha/{fecha}")
    public MontoCantidadDTO balanceDiario(@PathVariable LocalDate fecha){
        return ventaServ.balanceDiario(fecha);
    }
    
    
    @GetMapping("/ventas/mayor_venta")
    public List<VentaClienteDTO> mayorVenta(){
        return ventaServ.ventaMasAlta();
    }
    
    
    
    
}
