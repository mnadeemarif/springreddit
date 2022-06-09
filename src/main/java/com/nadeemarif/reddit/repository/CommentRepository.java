package com.nadeemarif.reddit.repository;

import com.nadeemarif.reddit.model.Comment;
import com.nadeemarif.reddit.model.Post;
import com.nadeemarif.reddit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
    List<Comment> findAllByUser(User user);
}
