package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Recv;

@Repository
public interface RecvRepository extends JpaRepository<Recv, Long> {

}
