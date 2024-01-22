
package com.example.bazar.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MontoCantidadDTO {
    private int cantVentasDTO;
    private int sumaVentasDTO;

    public MontoCantidadDTO() {
    }

    public MontoCantidadDTO(int cantVentasDTO, int sumaVentasDTO) {
        this.cantVentasDTO = cantVentasDTO;
        this.sumaVentasDTO = sumaVentasDTO;
    }
    
    
    
    
}
