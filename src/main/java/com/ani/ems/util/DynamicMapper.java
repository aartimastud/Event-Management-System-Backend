package com.ani.ems.util;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class DynamicMapper {

    public <Entity, DTO> DTO convertor(Entity entity, DTO dto) {
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}
