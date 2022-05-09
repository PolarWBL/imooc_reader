package com.ctgu.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ctgu.reader.entity.Book;
import com.ctgu.reader.entity.Evaluation;
import com.ctgu.reader.mapper.BookMapper;
import com.ctgu.reader.mapper.EvaluationMapper;
import com.ctgu.reader.mapper.MemberMapper;
import com.ctgu.reader.service.EvaluationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Boliang Weng
 */
@Service("evaluationService")
public class EvaluationServiceImpl implements EvaluationService {
    @Resource
    private EvaluationMapper evaluationMapper;
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private BookMapper bookMapper;

    /**
     * 通过bookId查询该书的有效评论
     *
     * @param bookId 书的ID
     * @return 返回评论List对象
     */
    @Override
    public List<Evaluation> selectByBookId(Long bookId) {
        Book book = bookMapper.selectById(bookId);

        QueryWrapper<Evaluation> wrapper = new QueryWrapper<>();
        wrapper.eq("book_id", bookId);
        wrapper.eq("state", "enable");
        wrapper.orderByDesc("create_time");
        List<Evaluation> evaluationList = evaluationMapper.selectList(wrapper);
        for (Evaluation evaluation : evaluationList) {
            evaluation.setBook(book);
            evaluation.setMember(memberMapper.selectById(evaluation.getMemberId()));
        }
        return evaluationList;
    }

    /**
     * 短评分页
     *
     * @param page 页码
     * @param rows 每页数据量
     * @return IPage分页对象
     */
    @Override
    public IPage<Evaluation> paging(Integer page, Integer rows) {
        Page<Evaluation> evaluationPage = new Page<>(page, rows);
        QueryWrapper<Evaluation> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");
        return evaluationMapper.selectPage(evaluationPage, wrapper);
    }

    /**
     *  禁用评论
     * @param evaluation 要禁用的评论
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void update(Evaluation evaluation) {
        evaluationMapper.updateById(evaluation);
    }

    /**
     * 通过评论id查询评论
     *
     * @param evaluationId 评论id
     * @return 返回评论实体类
     */
    @Override
    public Evaluation selectById(Long evaluationId) {
        return evaluationMapper.selectById(evaluationId);
    }
}
