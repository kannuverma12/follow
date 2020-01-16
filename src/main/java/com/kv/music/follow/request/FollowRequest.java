package com.kv.music.follow.request;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Validated
public class FollowRequest {

    @NotBlank
    private String user;

    @NotEmpty
    private List<String> artist;
}
