package com.lexuefa.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lexuefa.comment.result.ResultObject;
import com.lexuefa.controller.requestEntity.LegalReqParam;
import com.lexuefa.entity.LegalEntity;
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
    
    @PostMapping("/queryPageLaw")
    public ResultObject<List<LegalEntity>> queryPageLaw(@RequestBody LegalReqParam legalReqParam){
        Page<LegalEntity> legalEntityPage = legalService.queryPageLaw(legalReqParam);
        return ResultObject.success(legalEntityPage.getRecords());
    }
}
