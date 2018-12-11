package com.ucbcba.logindemo.repositories;

import com.ucbcba.logindemo.entities.FavoritePost;
import com.ucbcba.logindemo.entities.SharePost;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

@Transactional
public interface SharePostRepository extends CrudRepository<SharePost,Integer> {
    @Query("SELECT u FROM SharePost u WHERE  u.post = :post")
    SharePost findSharedPostById(
            @Param("post") Integer post);
           // @Param("user") Integer user);


   // @Query("SELECT DISTINCT r.post FROM SharePost r")
   // Iterable<Integer> findDistinctStates();
}
