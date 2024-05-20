package com.yupi.usercenter.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageRequest implements Serializable {

    private static final long serialVersionUID = 832638458561609280L;
    protected int PageSize = 10;

    protected int PageNum = 1;


}
