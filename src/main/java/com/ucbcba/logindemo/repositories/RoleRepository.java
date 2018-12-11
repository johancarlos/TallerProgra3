package com.ucbcba.logindemo.repositories;

import com.ucbcba.logindemo.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
