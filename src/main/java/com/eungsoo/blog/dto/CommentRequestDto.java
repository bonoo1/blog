package com.eungsoo.blog.dto;

import com.eungsoo.blog.models.Contents;
import com.eungsoo.blog.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentRequestDto {
    private String text;
    private User user;
    private Contents contents;
}
