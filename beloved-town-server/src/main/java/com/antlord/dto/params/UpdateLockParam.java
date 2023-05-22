package com.antlord.dto.params;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UpdateLockParam implements Serializable {
    private static final long serialVersionUID = 5665554084729546781L;

    /**
     * 用户id
     */
    private List<String> userIdList;

    /**
     * 是否锁定[1-锁定,0-正常]
     */
    private Integer lockFlag;
}
