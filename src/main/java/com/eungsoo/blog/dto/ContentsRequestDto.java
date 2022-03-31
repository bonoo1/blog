package com.eungsoo.blog.dto;

import com.eungsoo.blog.models.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ContentsRequestDto {
    private String title;
    private User user;
    private String contents;
}