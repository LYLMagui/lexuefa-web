package com.lexuefa.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lexuefa.controller.requestEntity.LegalReqParam;
import com.lexuefa.entity.LegalEntity;
import com.lexuefa.entity.LegalType;

import java.util.List;

/**
 * 法律业务逻辑Service类
 * @author ukir
 * @date 2023/04/19 20:53
 **/
public interface LegalService extends IService<LegalEntity> {
    /**
     * 查询法律列表
     * @param legalReqParam
     * @return
     */
    Page<LegalEntity> queryLawList(LegalReqParam legalReqParam);

    /**
     * 查询法律分类列表
     * @return
     */
    List<LegalType> queryLawType();

    /**
     * 查询法律二级分类列表
     * @return
     */
    List<LegalType> querySecType(String secType);

    /**
     * 根据内容查询法律
     * @param legalReqParam
     * @return
     */
    Page<LegalEntity> queryLawsByArticle(LegalReqParam legalReqParam);
}
