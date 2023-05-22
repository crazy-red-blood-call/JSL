package com.antlord.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import com.antlord.entity.Manicure;

/**
 * Description: 美甲数据库处理类
 * @Author: author
 * Version: 1.0
 * Create Date Time: 2023-05-13 20:23:25
 *
 */
@Mapper
public interface ManicureMapper {

    /**
     * 新增美甲
     */
    void addManicure(@Param("param") Manicure manicure);

    /**
     * 查询美甲
     */
    List<Manicure> getManicureList(Manicure manicure);

    /**
     * 更新美甲
     */
    void updateManicure(@Param("param") Manicure manicure);

    /**
     * 删除美甲
     */
    void deleteManicure(@Param("idList") List<String> idList);

}