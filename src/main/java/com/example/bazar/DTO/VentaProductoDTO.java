
package com.example.bazar.DTO;


import com.example.bazar.Entities.Producto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VentaProductoDTO {
    
    private Long intVentaDTO;
    private List<Producto> listaProductosDTO;

    public VentaProductoDTO() {
    }

    public VentaProductoDTO(Long intVentaDTO, List<Producto> listaProductosDTO) {
        this.intVentaDTO = intVentaDTO;
        this.listaProductosDTO = listaProductosDTO;
    }








    
    
}
