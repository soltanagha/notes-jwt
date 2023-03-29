package com.soomee.notesjwt.repository;

import com.soomee.notesjwt.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface CommentRepository extends MongoRepository<Comment, String> {

}
