package com.example.demo.repository;

import com.example.demo.entity.ParentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ParentEntityRepository extends JpaRepository<ParentEntity, UUID> {
}
