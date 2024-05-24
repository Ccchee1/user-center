package com.yupi.usercenter.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class DeleteRequest implements Serializable {
    private static final long serialVersionUID = 8819878220200275902L;

    private Long id;
}
