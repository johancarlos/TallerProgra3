package com.ucbcba.logindemo.repositories;

import com.ucbcba.logindemo.entities.Comment;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface CommentRepository extends CrudRepository<Comment,Integer> {
}
