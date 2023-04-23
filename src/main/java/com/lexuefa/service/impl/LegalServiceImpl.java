package com.lexuefa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lexuefa.controller.requestEntity.LegalReqParam;
import com.lexuefa.dao.LegalDao;
import com.lexuefa.dao.LegalTypeDao;
import com.lexuefa.entity.LegalEntity;
import com.lexuefa.entity.LegalType;
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
    
    @Autowired
    private LegalTypeDao legalTypeDao;


    /**
     * 查询法律列表
     * @param legalReqParam
     * @return
     */
    @Override
    public Page<LegalEntity> queryLawList(LegalReqParam legalReqParam) {
        Page<LegalEntity> page = new Page<>(legalReqParam.getPageNo(),legalReqParam.getPageSize());
        LambdaQueryWrapper<LegalEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(legalReqParam.legalName),LegalEntity::getLegalName,legalReqParam.legalName)
                .eq(LegalEntity::getSecondCategory,legalReqParam.getSecondCategory());
        return legalDao.selectPage(page, wrapper);
    }

    /**
     * 查询法律分类列表
     * @return
     */
    @Override
    public List<LegalType> queryLawType() {
        LambdaQueryWrapper<LegalType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(LegalType::getId, LegalType::getTopCategory, LegalType::getTopName)
                .groupBy(LegalType::getTopName)
                .orderByAsc(LegalType::getId);
        return legalTypeDao.selectList(queryWrapper);
    }

    @Override
    public List<LegalType> querySecType(String secType) {
        LambdaQueryWrapper<LegalType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(LegalType::getSecondName, LegalType::getSecondCategory)
                .eq(LegalType::getTopCategory,secType);
        return legalTypeDao.selectList(queryWrapper);
    }

    @Override
    public Page<LegalEntity> queryLawsByArticle(LegalReqParam legalReqParam) {
        Page<LegalEntity> page = new Page<>();
        return legalDao.queryLawsByArticle(page,legalReqParam);
    }
}
