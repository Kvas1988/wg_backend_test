package com.example.wg_backend.repository;

import com.example.wg_backend.model.Cat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatRepository extends JpaRepository<Cat, String> {
}
