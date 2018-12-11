package com.ucbcba.logindemo.services;

import com.ucbcba.logindemo.entities.Comment;

public interface CommentService {

    Iterable<Comment> listAllComments();

    void saveComment(Comment comment);

    Comment getComment(Integer id);

    void deleteComment(Integer id);
}
