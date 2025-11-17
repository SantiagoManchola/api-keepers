package com.example.keepers.controller;

import com.example.keepers.model.Keeper;
import com.example.keepers.service.KeeperService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/keepers")
@CrossOrigin(origins = "*")
public class KeeperController {

    @Autowired
    private KeeperService keeperService;

    // GET /keepers - Obtener todos los cuidadores (con filtro opcional por estado)
    @GetMapping
    public ResponseEntity<List<Keeper>> getAllKeepers(
            @RequestParam(required = false) Boolean is_active) {
        
        List<Keeper> keepers;
        if (is_active != null) {
            keepers = keeperService.getKeepersByActiveStatus(is_active);
        } else {
            keepers = keeperService.getAllKeepers();
        }
        return ResponseEntity.ok(keepers);
    }

    // GET /keepers/{id} - Obtener cuidador por ID
    @GetMapping("/{id}")
    public ResponseEntity<Keeper> getKeeperById(@PathVariable Long id) {
        Keeper keeper = keeperService.getKeeperById(id);
        return ResponseEntity.ok(keeper);
    }

    // GET /keepers/search/by-specialization - Buscar por especialización
    @GetMapping("/search/by-specialization")
    public ResponseEntity<List<Keeper>> getKeepersBySpecialization(
            @RequestParam String specialization) {
        List<Keeper> keepers = keeperService.getKeepersBySpecialization(specialization);
        return ResponseEntity.ok(keepers);
    }

    // GET /keepers/search/by-experience - Buscar por experiencia mínima
    @GetMapping("/search/by-experience")
    public ResponseEntity<List<Keeper>> getKeepersByMinimumExperience(
            @RequestParam Integer min_years) {
        List<Keeper> keepers = keeperService.getKeepersByMinimumExperience(min_years);
        return ResponseEntity.ok(keepers);
    }

    // GET /keepers/search/by-name - Buscar por nombre
    @GetMapping("/search/by-name")
    public ResponseEntity<List<Keeper>> searchKeepersByName(
            @RequestParam String name) {
        List<Keeper> keepers = keeperService.searchKeepersByName(name);
        return ResponseEntity.ok(keepers);
    }

    // GET /keepers/search/active-by-specialization - Buscar cuidadores activos con especialización
    @GetMapping("/search/active-by-specialization")
    public ResponseEntity<List<Keeper>> getActiveKeepersBySpecialization(
            @RequestParam String specialization) {
        List<Keeper> keepers = keeperService.getActiveKeepersBySpecialization(specialization);
        return ResponseEntity.ok(keepers);
    }

    // POST /keepers - Crear nuevo cuidador
    @PostMapping
    public ResponseEntity<Keeper> createKeeper(@Valid @RequestBody Keeper keeper) {
        Keeper newKeeper = keeperService.createKeeper(keeper);
        return ResponseEntity.status(HttpStatus.CREATED).body(newKeeper);
    }

    // PUT /keepers/{id} - Actualizar cuidador existente
    @PutMapping("/{id}")
    public ResponseEntity<Keeper> updateKeeper(
            @PathVariable Long id,
            @Valid @RequestBody Keeper keeperDetails) {
        Keeper updatedKeeper = keeperService.updateKeeper(id, keeperDetails);
        return ResponseEntity.ok(updatedKeeper);
    }

    // DELETE /keepers/{id} - Eliminar cuidador
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKeeper(@PathVariable Long id) {
        keeperService.deleteKeeper(id);
        return ResponseEntity.noContent().build();
    }

    // HEAD /keepers/{id} - Verificar si existe un cuidador
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> keeperExists(@PathVariable Long id) {
        if (keeperService.keeperExists(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
