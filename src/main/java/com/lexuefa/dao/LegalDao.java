package com.lexuefa.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lexuefa.entity.LegalEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 法律 DAO 接口
 * @author ukir
 * @date 2023/04/19 17:02
 **/
@Mapper
public interface LegalDao extends BaseMapper<LegalEntity> {
}
