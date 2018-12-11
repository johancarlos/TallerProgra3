package com.ucbcba.logindemo.repositories;

import com.ucbcba.logindemo.entities.Post;
import com.ucbcba.logindemo.entities.ReportedPost;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ReportedPostRepository extends CrudRepository<ReportedPost, Integer>{
    @Query("SELECT u FROM ReportedPost u WHERE u.user = :user and u.post = :post")
    ReportedPost findUserByStatusAndNameNamedParams(
            @Param("user") Integer user,
            @Param("post") Integer post);

//    @Query("select distinct p.id from post p")
//    List<ReportedPost> findDistinctPosts();
    @Query("SELECT DISTINCT r.post FROM ReportedPost r")
    Iterable<Integer> findDistinctStates();
}
