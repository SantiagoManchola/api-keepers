package com.example.keepers.service;

import com.example.keepers.exception.KeeperEmailAlreadyExistsException;
import com.example.keepers.exception.KeeperIdAlreadyExistsException;
import com.example.keepers.exception.KeeperNotFoundException;
import com.example.keepers.model.Keeper;
import com.example.keepers.repository.KeeperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KeeperService {

    @Autowired
    private KeeperRepository keeperRepository;

    // Obtener todos los cuidadores
    public List<Keeper> getAllKeepers() {
        return keeperRepository.findAll();
    }

    // Obtener cuidadores por estado (activo/inactivo)
    public List<Keeper> getKeepersByActiveStatus(Boolean isActive) {
        return keeperRepository.findByIsActive(isActive);
    }

    // Obtener cuidador por ID
    public Keeper getKeeperById(Long id) {
        return keeperRepository.findById(id)
                .orElseThrow(() -> new KeeperNotFoundException(id));
    }

    // Buscar por email
    public Keeper getKeeperByEmail(String email) {
        return keeperRepository.findByEmail(email)
                .orElseThrow(() -> new KeeperNotFoundException("Keeper with email " + email + " not found"));
    }

    // Buscar por especialización
    public List<Keeper> getKeepersBySpecialization(String specialization) {
        return keeperRepository.findBySpecialization(specialization);
    }

    // Buscar por experiencia mínima
    public List<Keeper> getKeepersByMinimumExperience(Integer minYears) {
        return keeperRepository.findByMinimumExperience(minYears);
    }

    // Buscar por nombre (parcial)
    public List<Keeper> searchKeepersByName(String name) {
        return keeperRepository.findByNameContaining(name);
    }

    // Buscar cuidadores activos con especialización específica
    public List<Keeper> getActiveKeepersBySpecialization(String specialization) {
        return keeperRepository.findByIsActiveAndSpecialization(true, specialization);
    }

    // Crear nuevo cuidador
    public Keeper createKeeper(Keeper keeper) {
        // Verificar si el ID ya existe
        if (keeperRepository.existsById(keeper.getId())) {
            throw new KeeperIdAlreadyExistsException(keeper.getId());
        }
        
        // Verificar si el email ya existe
        if (keeperRepository.findByEmail(keeper.getEmail()).isPresent()) {
            throw new KeeperEmailAlreadyExistsException(keeper.getEmail());
        }
        
        return keeperRepository.save(keeper);
    }

    // Actualizar cuidador existente
    public Keeper updateKeeper(Long id, Keeper keeperDetails) {
        Keeper keeper = keeperRepository.findById(id)
                .orElseThrow(() -> new KeeperNotFoundException(id));

        // Verificar si el email ya existe en otro cuidador
        Optional<Keeper> existingKeeperWithEmail = keeperRepository.findByEmail(keeperDetails.getEmail());
        if (existingKeeperWithEmail.isPresent() && !existingKeeperWithEmail.get().getId().equals(id)) {
            throw new KeeperEmailAlreadyExistsException(keeperDetails.getEmail());
        }

        // Actualizar campos
        keeper.setFirstName(keeperDetails.getFirstName());
        keeper.setLastName(keeperDetails.getLastName());
        keeper.setEmail(keeperDetails.getEmail());
        keeper.setHireDate(keeperDetails.getHireDate());
        keeper.setSpecialization(keeperDetails.getSpecialization());
        keeper.setIsActive(keeperDetails.getIsActive());
        keeper.setYearsOfExperience(keeperDetails.getYearsOfExperience());

        return keeperRepository.save(keeper);
    }

    // Eliminar cuidador
    public void deleteKeeper(Long id) {
        if (!keeperRepository.existsById(id)) {
            throw new KeeperNotFoundException(id);
        }
        keeperRepository.deleteById(id);
    }

    // Verificar si un cuidador existe
    public boolean keeperExists(Long id) {
        return keeperRepository.existsById(id);
    }
}
