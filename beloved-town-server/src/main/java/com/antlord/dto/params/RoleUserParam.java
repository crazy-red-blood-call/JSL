package com.antlord.dto.params;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RoleUserParam implements Serializable {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 角色id
     */
    private List<String> roleIdList;
    private String roleId;
}
