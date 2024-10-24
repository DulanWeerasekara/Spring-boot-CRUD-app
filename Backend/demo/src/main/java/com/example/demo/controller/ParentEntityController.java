package com.example.demo.controller;

import com.example.demo.entity.ParentEntity;
import com.example.demo.service.ParentEntityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/parents")
@CrossOrigin(origins = "http://localhost:3000")
public class ParentEntityController {

    private final ParentEntityService parentEntityService;

    public ParentEntityController(ParentEntityService parentEntityService) {
        this.parentEntityService = parentEntityService;
    }

    @GetMapping
    public List<ParentEntity> getAllParents() {
        return parentEntityService.getAllParents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParentEntity> getParentById(@PathVariable UUID id) {
        Optional<ParentEntity> parent = parentEntityService.getParentById(id);
        return parent.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ParentEntity> createParent(@RequestBody ParentEntity parentEntity) {
        ParentEntity createdParent = parentEntityService.createParent(parentEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdParent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParentEntity> updateParent(@PathVariable UUID id, @RequestBody ParentEntity updatedParent) {
        ParentEntity updated = parentEntityService.updateParent(id, updatedParent);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteParent(@PathVariable UUID id) {
        try {
            parentEntityService.deleteParent(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (RuntimeException e) {
            // Return a Bad Request response with the error message if deletion fails
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
