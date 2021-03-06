package com.eungsoo.blog.repository;

import com.eungsoo.blog.models.Contents;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ContentsRepository extends JpaRepository<Contents, Long> {
    Page<Contents> findAllByOrderByModifiedAtDesc(Pageable pageable);
}