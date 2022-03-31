package com.eungsoo.blog.repository;

import com.eungsoo.blog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByKakaoId(Long kakaoId);
    //Optional<User> 유저 객체에서 username을 찾은뒤 null값일 경우 예외처리를 하지않아도 오류?가나지않는다.
}