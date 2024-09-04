package com.demo.ExpenseManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.ExpenseManagement.entity.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
