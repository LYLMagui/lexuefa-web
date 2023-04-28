package com.lexuefa.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lexuefa.comment.result.ResultObject;
import com.lexuefa.controller.reqEntity.LegalReq;
import com.lexuefa.entity.legal.Chapter;
import com.lexuefa.entity.legal.Legal;
import com.lexuefa.entity.legal.LegalType;
import com.lexuefa.service.LegalService;
import org.springframework.beans.factory.annotation.Autowired;
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
     * @param legalReq
     * @return
     */
    @PostMapping("/queryLawList")
    public ResultObject<List<Legal>> queryLawList(@RequestBody LegalReq legalReq){
        Page<Legal> legalEntityPage = legalService.queryLawList(legalReq);
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
     * @param legalReq
     * @return
     */
    @PostMapping("/queryLawsByArticle")
    public ResultObject<List<Legal>> queryLawsByArticle(@RequestBody LegalReq legalReq){
        Page<Legal> legalEntityPage = legalService.queryLawsByArticle(legalReq);
        return ResultObject.success(legalEntityPage.getRecords());
    }

    @PostMapping("/queryLegalContent")
    public ResultObject<List<Chapter>> queryLegalContent(@RequestBody LegalReq legalReq){
        List<Chapter> chapters = legalService.querylegalContent(legalReq);
        return ResultObject.success(chapters);
    }
    
}
