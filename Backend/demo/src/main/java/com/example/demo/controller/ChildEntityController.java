package com.example.demo.controller;

import com.example.demo.entity.ChildEntity;
import com.example.demo.service.ChildEntityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/children")
@CrossOrigin(origins = "http://localhost:3000")
public class ChildEntityController {

    private final ChildEntityService childEntityService;

    public ChildEntityController(ChildEntityService childEntityService) {
        this.childEntityService = childEntityService;
    }

    @GetMapping
    public List<ChildEntity> getAllChildren() {
        return childEntityService.getAllChildren();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChildEntity> getChildById(@PathVariable UUID id) {
        Optional<ChildEntity> child = childEntityService.getChildById(id);
        return child.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ChildEntity createChild(@RequestBody ChildEntity childEntity) {
        return childEntityService.createChild(childEntity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChildEntity> updateChild(@PathVariable UUID id, @RequestBody ChildEntity updatedChild) {
        return ResponseEntity.ok(childEntityService.updateChild(id, updatedChild));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChild(@PathVariable UUID id) {
        childEntityService.deleteChild(id);
        return ResponseEntity.noContent().build();
    }
}
