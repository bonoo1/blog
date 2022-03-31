package com.eungsoo.blog.models;

import com.eungsoo.blog.dto.SignupRequestDto;
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
public class User {

    // ID가 자동으로 생성 및 증가합니다. 자동으로 1씩증가?
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    // nullable: null 허용 여부
// unique: 중복 허용 여부 (false 일때 중복 허용)
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = true)
    private Long kakaoId;

    @OneToMany(mappedBy = "user")
    List<Contents> contents = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    List<Comment> comment = new ArrayList<>();

//    @Column(nullable = false)
//    @Enumerated(value = EnumType.STRING)//db값들로 오갈때 String 으로 자동 변환해주는 어노테이션?
//    private UserRoleEnum role; // enum = enum 클래스의 객체값?들을 하나는 포함해야하는것


    public User(SignupRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
        this.email = requestDto.getEmail();
        this.kakaoId = requestDto.getKakaoId();
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;

    }

    public User(String username, String password, String email, Long kakaoId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.kakaoId = kakaoId;
    }
}
