package com.ucbcba.logindemo.repositories;

import com.ucbcba.logindemo.entities.FavoritePost;
import com.ucbcba.logindemo.entities.ReportedPost;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

@Transactional
public interface FavoritePostRepository extends CrudRepository<FavoritePost, Integer> {
    @Query("SELECT u FROM FavoritePost u WHERE u.user = :user and u.post = :post")
    FavoritePost findUserByStatusAndNameNamedParams(
            @Param("post") Integer post,
            @Param("user") Integer user);


    //    @Query("select distinct p.id from post p")
//    List<ReportedPost> findDistinctPosts();
    @Query("SELECT DISTINCT r.post FROM FavoritePost r")
    Iterable<Integer> findDistinctStates();
}
