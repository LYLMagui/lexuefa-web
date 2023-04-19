package com.lexuefa.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lexuefa.controller.requestEntity.LegalReqParam;
import com.lexuefa.entity.LegalEntity;

/**
 * 法律业务逻辑Service类
 * @author ukir
 * @date 2023/04/19 20:53
 **/
public interface LegalService extends IService<LegalEntity> {
    Page<LegalEntity> queryPageLaw(LegalReqParam legalReqParam);
}
