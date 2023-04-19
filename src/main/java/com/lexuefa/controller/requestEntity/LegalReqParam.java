package com.lexuefa.controller.requestEntity;

import lombok.Data;

/**
 * @author ukir
 * @date 2023/04/19 21:08
 **/
@Data
public class LegalReqParam {
    /**
     * 法律标题
     */
    public String legalTitle;

    /**
     * 法律内容
     */
    public String legalContent;
    
    public int pageNo;
    
    public int pageSize;
    
}
