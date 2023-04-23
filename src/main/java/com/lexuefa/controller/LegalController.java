package com.lexuefa.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lexuefa.comment.result.ResultObject;
import com.lexuefa.controller.requestEntity.LegalReqParam;
import com.lexuefa.entity.LegalEntity;
import com.lexuefa.entity.LegalType;
import com.lexuefa.service.LegalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 法律 Controller 
 * @author ukir
 * @date 2023/04/19 16:44
 **/
@RestController
@RequestMapping("/legal")
public class LegalController {
    @Autowired
    private LegalService legalService;

    /**
     * 获取法律列表
     * @param legalReqParam
     * @return
     */
    @PostMapping("/queryLawList")
    public ResultObject<List<LegalEntity>> queryLawList(@RequestBody LegalReqParam legalReqParam){
        Page<LegalEntity> legalEntityPage = legalService.queryLawList(legalReqParam);
        return ResultObject.success(legalEntityPage.getRecords());
    }

    /**
     * 获取一级分类列表
     * @return
     */
    @GetMapping("/queryLawType")
    public ResultObject<List<LegalType>> queryLawType(){
        List<LegalType> legalTypes = legalService.queryLawType();
        return ResultObject.success(legalTypes);
    }

    /**
     * 获取二级分类列表
     * @param secType
     * @return
     */
    @GetMapping("/querySecType/{secType}")
    public ResultObject<List<LegalType>> querySecType(@PathVariable("secType") String secType){
        List<LegalType> sceType = legalService.querySecType(secType);
        return ResultObject.success(sceType);
    }

    /**
     * 根据内容查询法律
     * @param legalReqParam
     * @return
     */
    @PostMapping("/queryLawsByArticle")
    public ResultObject<List<LegalEntity>> queryLawsByArticle(@RequestBody LegalReqParam legalReqParam){
        Page<LegalEntity> legalEntityPage = legalService.queryLawsByArticle(legalReqParam);
        return ResultObject.success(legalEntityPage.getRecords());
    }
    
}
