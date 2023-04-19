package com.lexuefa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lexuefa.controller.requestEntity.LegalReqParam;
import com.lexuefa.dao.LegalDao;
import com.lexuefa.entity.LegalEntity;
import com.lexuefa.service.LegalService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 法律业务逻辑Service实现类
 * 
 * @author ukir
 * @date 2023/04/19 20:56
 **/
@Service 
public class LegalServiceImpl extends ServiceImpl<LegalDao, LegalEntity> implements LegalService {
    
    @Autowired 
    private LegalDao legalDao;


    @Override
    public Page<LegalEntity> queryPageLaw(LegalReqParam legalReqParam) {
        Page<LegalEntity> page = new Page<>(legalReqParam.getPageNo(),legalReqParam.getPageSize());
        LambdaQueryWrapper<LegalEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(legalReqParam.getLegalContent()),LegalEntity::getArticle,legalReqParam.getLegalContent())
                .like(StringUtils.isNotBlank(legalReqParam.getLegalTitle()), LegalEntity::getLegalName, legalReqParam.getLegalTitle());
        return legalDao.selectPage(page, wrapper);
    }
}
