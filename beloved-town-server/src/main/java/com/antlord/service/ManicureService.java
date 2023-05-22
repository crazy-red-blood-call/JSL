package com.antlord.service;
import com.antlord.bean.Result;
import java.util.List;
import com.antlord.entity.Manicure;

/**
 * Description: 美甲服务类
 * @Author: author
 * Version: 1.0
 * Create Date Time: 2023-05-13 20:23:25
 *
 */
public interface ManicureService {

    Result addManicure(Manicure manicure);

    Result getManicureList(Manicure manicure);

    Result updateManicure(Manicure manicure);

    Result deleteManicure(List<String> idList);

}