package com.eungsoo.blog.repository;

import com.eungsoo.blog.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByContentsIdOrderByModifiedAtDesc(Long id);
}
