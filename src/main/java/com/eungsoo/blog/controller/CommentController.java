package com.eungsoo.blog.controller;

import com.eungsoo.blog.dto.CommentRequestDto;
import com.eungsoo.blog.models.Comment;
import com.eungsoo.blog.models.Contents;
import com.eungsoo.blog.repository.CommentRepository;
import com.eungsoo.blog.repository.ContentsRepository;
import com.eungsoo.blog.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class CommentController {

    @Autowired// 필요한 의존객체 타입에 해당하는 빈을 찾아 주입.
    CommentRepository commentRepository;

    @Autowired
    ContentsRepository contentsRepository;

    @PostMapping("/api/contents/{id}/comment")//@AuthenticationPrincipal 로그인한 사용자 정보 파라메터로 받음.
    public String createComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @ModelAttribute CommentRequestDto requestDto){
        Comment comment = new Comment(requestDto);
        Contents contents = contentsRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        comment.setUser(userDetails.getUser());
        comment.setContents(contents);
        commentRepository.save(comment);
        return "redirect:/api/contents/{id}";
    }

    @PutMapping("/api/contents/{id}/comment/{commentId}")
    public String editComment(@PathVariable Long commentId, @ModelAttribute CommentRequestDto requestDto){
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );

        comment.setText(requestDto.getText());

        commentRepository.save(comment);
        return "redirect:/api/contents/{id}";
    }

    @DeleteMapping("/api/contents/{id}/comment/{commentId}")
    public String deleteComment(@PathVariable Long commentId){
        commentRepository.deleteById(commentId);
        return "redirect:/api/contents/{id}";
    }
}
