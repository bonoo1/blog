package com.eungsoo.blog.controller;


import com.eungsoo.blog.models.Comment;
import com.eungsoo.blog.models.Contents;
import com.eungsoo.blog.repository.CommentRepository;
import com.eungsoo.blog.repository.ContentsRepository;
import com.eungsoo.blog.dto.ContentsRequestDto;
import com.eungsoo.blog.repository.UserRepository;
import com.eungsoo.blog.security.UserDetailsImpl;
import com.eungsoo.blog.service.ContentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//@ResponseBody 이거 추가하면 타임리프 작동을 안한다

public class ContentsController {

    @Autowired
    ContentsRepository contentsRepository;

    @Autowired
    ContentsService contentsService;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    //게시글 전체 조회
//    @GetMapping("/api/index")
//    public String getBoard(Model model){
//        List<Board> board = boardRepository.findAll();
//        model.addAttribute("board",board);
//        return "index";
//    }

//    @GetMapping("/")
//    public String getIndex(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails){
//        List<Board> board = boardRepository.findAll();
//        model.addAttribute("board",board);
//        model.addAttribute("username",userDetails.getUsername());
//        return "index";
//    }

    @GetMapping("/")
    public String getIndex(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails, @PageableDefault(size=5) Pageable pageable){
        Page<Contents> contents = contentsRepository.findAllByOrderByModifiedAtDesc(pageable);
        //board.getTotalElements(); // 전체데이터 건수

        if(userDetails == null){
            model.addAttribute("user","null");
        }else{

            model.addAttribute("user",userDetails.getUser().getUsername());
        }
        // 현재 페이지 넘버 - 4 (4는 임의로 정한값)을 뺀값을 보여줄 페이지 값에 첫번째 값으로 지정
        // 현재 페이지 넘버 + 4 을 더한값을 보여줄 페이지에서 끝값으로 표시
        int startPage = Math.max(1, contents.getPageable().getPageNumber() - 4); //그런데 음수가 나올수 있으니 max() 함수를 이용해 0보다 작은값은 나오지 않도록 만들어줌
        int endPage = Math.min(contents.getTotalPages(),contents.getPageable().getPageNumber() + 4); // 마찬가지로 최대페이지수를 초과하지않게 min()함수 걸어줌

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("contents",contents);
        return "index";
    }

    //게시글 작성 페이지
    @GetMapping("/api/contents")
    public String getNotice( Model model, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Contents contents = new Contents();

        if(userDetails == null){
            model.addAttribute("user","null");
        }else{

            model.addAttribute("user",userDetails.getUser().getUsername());
        }
        model.addAttribute("contents", contents);
        return "contents";
    }

    // 게시글 작성
    @PostMapping("/api/contents")
    public String createNotice(@AuthenticationPrincipal UserDetailsImpl userDetails,@ModelAttribute ContentsRequestDto requestDto){
        requestDto.setUser(userDetails.getUser());
        Contents contents = new Contents(requestDto);
        contentsRepository.save(contents);
        return "redirect:/";
    }



    //게시글 한개 조회페이지
    @GetMapping("/api/contents/{id}")
    public String getOneContents(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails){

        Contents contents = contentsRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        List<Comment> comment = commentRepository.findByContentsIdOrderByModifiedAtDesc(id);
        if(userDetails == null){
            model.addAttribute("user","null");
        }else{

            model.addAttribute("user",userDetails.getUser().getUsername());
        }
        model.addAttribute("editcomment",new Comment());
        model.addAttribute("postcomment",new Comment());
        model.addAttribute("comment", comment);
        model.addAttribute("contents",contents);
//        comment.get(0).getUser().getUsername()
        return "detail";
    }


    @GetMapping("/api/contents/{id}/edit")
    public String getEditContents(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Contents contents = contentsRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        if(userDetails == null){
            model.addAttribute("user","null");
        }else{

            model.addAttribute("user",userDetails.getUser().getUsername());
        }
        model.addAttribute("contents",contents);
        return "edit";
    }


    @PutMapping("/api/contents/{id}/edit")
    public String updateContents(@PathVariable Long id, @ModelAttribute ContentsRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        requestDto.setUser(userDetails.getUser());
        contentsService.update(id, requestDto);
        return "redirect:/";

    }

    @DeleteMapping("/api/contents/{id}")
    public String deleteBoard(@PathVariable Long id){
        contentsRepository.deleteById(id);
        return "redirect:/";
    }

}
