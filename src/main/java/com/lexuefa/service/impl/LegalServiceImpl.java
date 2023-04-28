package com.lexuefa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lexuefa.controller.reqEntity.LegalReq;
import com.lexuefa.dao.legal.ChapterDao;
import com.lexuefa.dao.legal.LegalDao;
import com.lexuefa.dao.legal.LegalTypeDao;
import com.lexuefa.entity.legal.*;
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
public class LegalServiceImpl extends ServiceImpl<LegalDao, Legal> implements LegalService {

    @Autowired
    private LegalDao legalDao;

    @Autowired
    private LegalTypeDao legalTypeDao;

    @Autowired
    private ChapterDao chapterDao;


    /**
     * 查询法律列表
     *
     * @param legalReq
     * @return
     */
    @Override
    public Page<Legal> queryLawList(LegalReq legalReq) {
        Page<Legal> page = new Page<>(legalReq.getPageNo(), legalReq.getPageSize());
        LambdaQueryWrapper<Legal> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(legalReq.legalName), Legal::getLegalName, legalReq.legalName)
                .eq(Legal::getSecondCategory, legalReq.getSecondCategory());
        return legalDao.selectPage(page, wrapper);
    }

    /**
     * 查询法律分类列表
     *
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

    /**
     * 查询法律二级分类列表
     *
     * @return
     */
    @Override
    public List<LegalType> querySecType(String secType) {
        LambdaQueryWrapper<LegalType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(LegalType::getSecondName, LegalType::getSecondCategory)
                .eq(LegalType::getTopCategory, secType);
        return legalTypeDao.selectList(queryWrapper);
    }

    /**
     * 根据内容查询法律
     *
     * @param legalReq
     * @return
     */
    @Override
    public Page<Legal> queryLawsByArticle(LegalReq legalReq) {
        Page<Legal> page = new Page<>();
        return legalDao.queryLawsByArticle(page, legalReq);
    }

    /**
     * 查询法律内容
     *
     * @param legalReq
     * @return
     */
    @Override
    public List<Chapter> querylegalContent(LegalReq legalReq) {
        List<Chapter> chapters = chapterDao.selectChapters(legalReq.getLegalNo());
        
        chapters.forEach(chapter -> {
            List<Section> sections = chapterDao.selectSection(chapter.getChapter(), chapter.getLegalNo());
            chapter.setSections(sections);
            if (chapter.getSections() != null && chapter.getSections().size() != 0) {
                sections.forEach(s -> {
                    List<Article> articles = chapterDao.selectAtricles(chapter.getChapter(),
                            chapter.getLegalNo(), s.getSectionNo());
                    s.setArticles(articles);
                });
                
            } else {
                List<Article> articles = chapterDao.selectAtricles(chapter.getChapter(),
                        chapter.getLegalNo(), null);
                chapter.setArticles(articles);
                
            }

        });
        return chapters;
    }
}
