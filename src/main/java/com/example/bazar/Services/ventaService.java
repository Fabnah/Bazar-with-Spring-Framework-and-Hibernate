package com.example.bazar.Services;

import com.example.bazar.DTO.VentaClienteDTO;
import com.example.bazar.DTO.MontoCantidadDTO;
import com.example.bazar.DTO.VentaProductoDTO;
import com.example.bazar.Entities.Cliente;
import com.example.bazar.Entities.Producto;
import com.example.bazar.Entities.Venta;
import com.example.bazar.Repositories.IclienteRepository;
import com.example.bazar.Repositories.IproductoRepository;
import com.example.bazar.Repositories.IventaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ventaService implements IventaService {

    @Autowired
    IventaRepository repo;

    @Autowired
    IclienteRepository clienteRepo;

    @Autowired
    IproductoRepository producRepo;

    @Autowired
    productoService prodServ;

    @Override
    public void agregarVentaService(Venta venta) {
        Venta ventaServ = new Venta();

        ventaServ.setFecha_venta(venta.getFecha_venta());

        
        Cliente cliente = venta.getUnCliente();
        if (cliente != null) {
            Long clienteId = cliente.getId_cliente();
            if (clienteId == null) {
                throw new IllegalArgumentException("ID de cliente no puede ser nulo");
            }
            
            //traigo a la entidad cliente de la base de datos antes de anclarla a la venta
            Cliente clientePersistente = clienteRepo.findById(clienteId)
                    .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));

            ventaServ.setUnCliente(clientePersistente);
        }

        List<Producto> productos = new ArrayList<>();
        Double totalCont = 0.0;
        if (venta.getListaProductos() != null) {

            for (Producto producto : venta.getListaProductos()) {
                Long productoId = producto.getCodigo_producto();

                if (productoId == null) {
                    throw new IllegalArgumentException("ID de producto no puede ser nulo");
                }

                //traigo la lista de productos de la base de datos antes de anclarla a la venta
                Producto productoPersistente = producRepo.findById(productoId)
                        .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

                productos.add(productoPersistente);
                //se descuenta una unidad de la cantidad de productos en la DB
                prodServ.descontarProducto(productoPersistente.getCodigo_producto());
                totalCont += productoPersistente.getCosto();
            }
        }

        ventaServ.setListaProductos(productos);

        ventaServ.setTotal(totalCont);

        repo.save(ventaServ);
    }

    @Override
    public Venta buscarVentaService(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public List<Venta> listarVentasService() {
        return repo.findAll();
    }

    @Override
    public void editarVentasService(Long id, LocalDate fecha_venta, Double total, List<Producto> listaProducto, Cliente cliente) {
        Venta venta = buscarVentaService(id);
        venta.setFecha_venta(fecha_venta);
        venta.setTotal(total);
        venta.setListaProductos(listaProducto);
        venta.setUnCliente(cliente);
        this.agregarVentaService(venta);
    }

    @Override
    public void eliminarVentaService(Long id) {
        Venta venta = buscarVentaService(id);
        //sumo en 1 cada producto de la venta eliminada
        List<Producto> listaProductos = venta.getListaProductos();
        for (Producto producto : listaProductos) {
            Long idProd = producto.getCodigo_producto();
            Producto prodPersist = producRepo.findById(idProd).orElse(null);
            prodPersist.setCant_disponible(prodPersist.getCant_disponible() + 1);
        }
        //limpio la lista de productos, asi no tengo referencias y puedo eliminar la venta
        venta.getListaProductos().clear();
        venta.setUnCliente(null);
        repo.delete(venta);
    }

    //lista de productos de una venta especifica
    @Override
    public VentaProductoDTO listaDeterminada(Long id) {
        Venta venta = buscarVentaService(id);
        VentaProductoDTO dto = new VentaProductoDTO();
        dto.setIntVentaDTO(venta.getCodigo_venta());
        dto.setListaProductosDTO(venta.getListaProductos());
        return dto;
    }

    //funcion que cuenta las ventas que se hicieron en X fecha, y la suma de éstas
    @Override
    public MontoCantidadDTO balanceDiario(LocalDate fecha) {
        MontoCantidadDTO dto = new MontoCantidadDTO();
        int cantVentas = 0;
        int sumaVentas = 0;
        for (Venta venta : listarVentasService()) {

            if (venta.getFecha_venta().equals(fecha)) {

                cantVentas++;
                sumaVentas += venta.getTotal();
            }

        }
        dto.setCantVentasDTO(cantVentas);
        dto.setSumaVentasDTO(sumaVentas);

        return dto;
    }

    //se hace una lista en caso de que haya mas de una venta con el valor mas alto
    @Override
    public List<VentaClienteDTO> ventaMasAlta() {
        //lista de ventaClienteDTO para retornar
        List<VentaClienteDTO> listaDTO = new ArrayList<>();
        //lista de ventas para calcular las ventas mas altas en caso de que haya mas de una
        List<Venta> listaVentaDTO = new ArrayList<>();
        
        Venta ventaConTotalMasAlto = null;

        for (Venta venta : listarVentasService()) {
            if (ventaConTotalMasAlto == null || venta.getTotal() > ventaConTotalMasAlto.getTotal()) {
                // Si la venta actual tiene un total más alto, actualizar la ventaConTotalMasAlto
                ventaConTotalMasAlto = venta;
                // Limpiar la lista y agregar la nueva venta
                listaVentaDTO.clear();
                listaVentaDTO.add(ventaConTotalMasAlto);
            } else if (venta.getTotal().equals(ventaConTotalMasAlto.getTotal())) {
                // Si la venta actual tiene el mismo total, agregarla a la lista
                listaVentaDTO.add(venta);
            }
        }

        for (Venta venta : listaVentaDTO) {
            System.out.println("cont");
            //se crea uno o varios dto para agregar a la listaDTO y retornarlo/s
            VentaClienteDTO dto = new VentaClienteDTO();
            //seteo el cod venta
            dto.setCodigo_venta(venta.getCodigo_venta());
            //seteo la cant de prod por venta
            int cantProd = 0;
            for (Producto producto : venta.getListaProductos()) {
                cantProd++;
            }
            dto.setCant_productos(cantProd);
            //seteo el total
            dto.setTotal(venta.getTotal());
            //seteo nombre y apellido del cliente
            dto.setNombre_cliente(venta.getUnCliente().getNombre());
            dto.setApellido_cliente(venta.getUnCliente().getApellido());

            listaDTO.add(dto);

        }

        return listaDTO;
    }

}
