package com.ucbcba.logindemo.repositories;

import com.ucbcba.logindemo.entities.HidedPost;
import com.ucbcba.logindemo.entities.ReportedPost;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

@Transactional
public interface HidedPostRepository extends CrudRepository<HidedPost, Integer> {
    @Query("SELECT u FROM HidedPost u WHERE u.user = :user and u.post = :post")
    HidedPost findHidedPostByUserId(
            @Param("user") Integer user,
            @Param("post") Integer post);
}
