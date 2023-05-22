package com.antlord.service.impl;

import com.antlord.mapper.DemoMapper;
import com.antlord.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DemoMapper demoMapper;

    @Override
    public String test(String param) {
        String city = demoMapper.getCity(param);
        log.debug("info===>{}", city);
        log.info("info===>{}", city);
        log.warn("info===>{}", city);
        log.error("info===>{}", city);
        return city;
    }
}
