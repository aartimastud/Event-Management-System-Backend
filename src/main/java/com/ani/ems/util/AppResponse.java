package com.ani.ems.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class AppResponse<T> {
    @Builder.Default
    private final String sts = "SUCCESS";
    private String msg;
    private T bd;

}
