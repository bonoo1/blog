package com.eungsoo.blog.models;


import com.eungsoo.blog.dto.ContentsRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
public class Contents extends Timestamped {

    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    // nullable = false 반드시 값을 가지도록 합니다.
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @ManyToOne //n:1 휴대폰은 자신을 소유한 회원이있음. 하지만 회원입장에선 여러개의 휴대폰을 소지했을수 있음.
//    @JoinColumn(nullable = false) 매니투원 어노테이션 사용시 생략가능?? 아직은 모름
    private User user;

    @OneToMany(mappedBy = "contents",cascade = CascadeType.REMOVE)//@manyToOne과 반대상항
    List<Comment> comment = new ArrayList<>();

    // 게시글 생성
    public Contents(ContentsRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.user = requestDto.getUser();
    }

    // 게시글 수정
    public void update(ContentsRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.user = requestDto.getUser();
    }
}