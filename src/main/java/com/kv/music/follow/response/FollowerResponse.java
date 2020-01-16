package com.kv.music.follow.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FollowerResponse {
    private String artist;
    private Integer   count;
    private String message;
    private String status;

}
