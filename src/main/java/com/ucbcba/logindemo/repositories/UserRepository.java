package com.ucbcba.logindemo.repositories;

import com.ucbcba.logindemo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    User findUserById(Integer id);
}