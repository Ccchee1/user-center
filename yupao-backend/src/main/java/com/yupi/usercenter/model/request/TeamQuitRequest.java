package com.yupi.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户添加队伍请求体
 *
 * @author yupi
 */
@Data
public class TeamQuitRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;
    /**
     * 队伍id
     */
    private Long teamId;

}