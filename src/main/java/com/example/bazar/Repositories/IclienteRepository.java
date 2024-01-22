
package com.example.bazar.Repositories;

import com.example.bazar.Entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IclienteRepository extends JpaRepository<Cliente, Long>{
    
}
