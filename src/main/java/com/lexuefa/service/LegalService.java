package com.lexuefa.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lexuefa.controller.reqEntity.LegalReq;
import com.lexuefa.entity.legal.Chapter;
import com.lexuefa.entity.legal.Legal;
import com.lexuefa.entity.legal.LegalType;

import java.util.List;

/**
 * 法律业务逻辑Service类
 * @author ukir
 * @date 2023/04/19 20:53
 **/
public interface LegalService extends IService<Legal> {
    /**
     * 查询法律列表
     * @param legalReq
     * @return
     */
    Page<Legal> queryLawList(LegalReq legalReq);

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
     * @param legalReq
     * @return
     */
    Page<Legal> queryLawsByArticle(LegalReq legalReq);

    /**
     * 查询法律内容
     * @param legalReq
     * @return
     */
    List<Chapter> querylegalContent(LegalReq legalReq);
}
