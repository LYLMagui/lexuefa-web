package com.lexuefa.dao.legal;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lexuefa.entity.legal.Article;
import com.lexuefa.entity.legal.Chapter;
import com.lexuefa.entity.legal.Section;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 法律章节DAO
 *
 * @author ukir
 * @date 2023/04/23 16:36
 **/
@Mapper
public interface ChapterDao extends BaseMapper<Chapter> {
    /**
     * 根据章节查询法律章节条文内容
     *
     * @param chapter
     * @param legalNo
     * @return
     */
    List<Article> selectAtricles(@Param("chapter") String chapter, @Param("legalNo") String legalNo, @Param("sectionNo") String sectionNo);

    /**
     * 查询法律章节信息
     *
     * @param legalNo
     * @return
     */
    List<Chapter> selectChapters(String legalNo);

    List<Section> selectSection(@Param("chapter") String chapter, @Param("legalNo") String legalNo);
}
