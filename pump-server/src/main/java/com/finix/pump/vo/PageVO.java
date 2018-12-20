package com.finix.pump.vo;

import lombok.Data;

import java.util.List;

/**
 * 分页
 *
 * @author zhangxiao04
 * @date 11/22/2018
 */
@Data
public class PageVO<T> {

    private List<T> content;
    private Long totalElements;
    private Integer totalPages;
    private Boolean last;
    private Boolean first;
    private Integer numberOfElements;
    private Integer size;
    private Integer number;

}