package com.example.keepers.repository;

import com.example.keepers.model.Keeper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KeeperRepository extends JpaRepository<Keeper, Long> {
    
    // Buscar por email (único)
    Optional<Keeper> findByEmail(String email);
    
    // Buscar por estado activo/inactivo
    List<Keeper> findByIsActive(Boolean isActive);
    
    // Buscar por especialización
    List<Keeper> findBySpecialization(String specialization);
    
    // Buscar cuidadores con experiencia mínima
    @Query("SELECT k FROM Keeper k WHERE k.yearsOfExperience >= :minYears ORDER BY k.yearsOfExperience DESC")
    List<Keeper> findByMinimumExperience(@Param("minYears") Integer minYears);
    
    // Buscar por nombre (parcial, case-insensitive)
    @Query("SELECT k FROM Keeper k WHERE LOWER(k.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(k.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Keeper> findByNameContaining(@Param("name") String name);
    
    // Buscar cuidadores activos con especialización específica
    List<Keeper> findByIsActiveAndSpecialization(Boolean isActive, String specialization);
}
