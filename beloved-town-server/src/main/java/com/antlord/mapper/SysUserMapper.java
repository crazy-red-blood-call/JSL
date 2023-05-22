package com.antlord.mapper;

import com.antlord.dto.params.RoleUserParam;
import com.antlord.dto.params.UpdateLockParam;
import com.antlord.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserMapper {

    /**
     * 精确查询
     * @param login
     * @return
     */
    List<SysUser> findPreciselyUserList(SysUser login);

    /**
     * 模糊查询
     * @param login
     * @return
     */
    List<SysUser> findFuzzyUserList(SysUser login);

    void saveUser(@Param("param") SysUser login, @Param("createBy") String createBy);

    void deleteUser(@Param("idList") List<String> idList, @Param("updateBy") String updateBy);

    void updateLock(@Param("param") UpdateLockParam param, @Param("updateBy") String updateBy);

    void updatePassword(@Param("userId") String userId, @Param("password") String password, @Param("updateBy") String updateBy);

    void updateUser(@Param("param") SysUser login, @Param("createBy") String createBy);

    void deleteAllRole(@Param("userId") String userId);

    void addRoles(@Param("roleUserParamList") List<RoleUserParam> roleUserParamList);

    List<String> getRoleNameList(@Param("userId") String userId);
}
