package com.ctgu.reader.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ctgu.reader.entity.Evaluation;

import java.util.List;

/**
 * @author Boliang Weng
 */
public interface EvaluationService {

    /**
     * 通过bookId查询该书的有效评论
     * @param bookId 书的ID
     * @return 返回评论List对象
     */
    public List<Evaluation> selectByBookId(Long bookId);

    /**
     * 短评分页
     * @param page 页码
     * @param rows 每页数据量
     * @return IPage分页对象
     */
    public IPage<Evaluation> paging(Integer page, Integer rows);

    /**
     *  禁用评论
     * @param evaluation 要禁用的评论
     */
    public void update(Evaluation evaluation);

    /**
     *  通过评论id查询评论
     * @param evaluationId 评论id
     * @return 返回评论实体类
     */
    public Evaluation selectById(Long evaluationId);
}

