package com.ctgu.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ctgu.reader.entity.Book;
import com.ctgu.reader.entity.Evaluation;
import com.ctgu.reader.entity.MemberReadState;
import com.ctgu.reader.mapper.BookMapper;
import com.ctgu.reader.mapper.EvaluationMapper;
import com.ctgu.reader.mapper.MemberReadStateMapper;
import com.ctgu.reader.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author Boliang Weng
 */
@Service("bookService")
@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
public class BookServiceImpl implements BookService {
    @Resource
    private BookMapper bookMapper;
    @Resource
    private EvaluationMapper evaluationMapper;
    @Resource
    private MemberReadStateMapper memberReadStateMapper;
    /**
     * 图书分页
     * @param page 页码
     * @param rows 每页数据量
     * @param categoryId 分类号
     * @param order 排序方式
     * @return IPage分页对象
     */
    @Override
    public IPage<Book> paging(Integer page, Integer rows, Long categoryId, String order) {
        Page<Book> bookPage = new Page<>(page, rows);
        QueryWrapper<Book> wrapper = new QueryWrapper<>();

        if (categoryId != null && categoryId != -1) {
            wrapper.eq("category_id", categoryId);
        }

        if ("quantity".equals(order)) {
            wrapper.orderByDesc("evaluation_quantity");
        } else if ("score".equals(order)) {
            wrapper.orderByDesc("evaluation_score");
        }

        return bookMapper.selectPage(bookPage, wrapper);
    }

    /**
     * 通过ID查询图书信息
     * @param bookId 图书id
     * @return book对象
     */
    @Override
    public Book selectById(Long bookId) {
        return bookMapper.selectById(bookId);
    }

    /**
     * 更新所有图书评分和评价数量信息
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateEvaluation() {
        bookMapper.updateEvaluation();
    }

    /**
     * 新增书记
     *
     * @param book 新增图书对象
     * @return 新增图书对象
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Book createBook(Book book) {
        bookMapper.insert(book);
        return book;
    }

    /**
     * 更新图书
     *
     * @param book 更新图书对象
     * @return 更新后的对象
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Book updateBook(Book book) {
        bookMapper.updateById(book);
        return book;
    }

    /**
     * 删除图书信息, 以及该图书的评论和会员对该书的阅读状态
     *
     * @param bookId 要删除的图书的ID
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteBook(Long bookId) {
        bookMapper.deleteById(bookId);
        QueryWrapper<Evaluation> evaluationQueryWrapper = new QueryWrapper<>();
        evaluationQueryWrapper.eq("book_id", bookId);
        evaluationMapper.delete(evaluationQueryWrapper);
        QueryWrapper<MemberReadState> memberReadStateQueryWrapper = new QueryWrapper<>();
        memberReadStateQueryWrapper.eq("book_id", bookId);
        memberReadStateMapper.delete(memberReadStateQueryWrapper);
    }
}
