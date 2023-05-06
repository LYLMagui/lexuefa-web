package com.lexuefa.dao.legal;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lexuefa.controller.reqEntity.LegalReq;
import com.lexuefa.entity.legal.Legal;
import org.apache.ibatis.annotations.Mapper;

/**
 * 法律 DAO 接口
 * @author ukir
 * @date 2023/04/19 17:02
 **/
@Mapper
public interface LegalDao extends BaseMapper<Legal> {

    /**
     * 通过内容查询法律
     * @param page
     * @param legalReq
     * @return
     */
    Page<Legal> queryLawsByArticle(IPage<Legal> page, LegalReq legalReq);

    /**
     * 最新法律列表
     * @return
     */
    Page<Legal> queryLastLaw(Page<Legal> page);

//    List<Chapter> querylegalContent(LegalReqParam legalReqParam);
}
