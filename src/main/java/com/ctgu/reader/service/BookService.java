package com.ctgu.reader.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ctgu.reader.entity.Book;

/**
 * 图书服务
 * @author Boliang Weng
 */
public interface BookService {

    /**
     * 图书分页
     * @param page 页码
     * @param rows 每页数据量
     * @param categoryId 分类号
     * @param order 排序方式
     * @return IPage分页对象
     */
    public IPage<Book> paging(Integer page, Integer rows, Long categoryId, String order);

    /**
     * 通过ID查询图书信息
     * @param bookId 图书id
     * @return book对象
     */
    public Book selectById(Long bookId);

    /**
     * 更新所有图书评分和评价数量信息
     */
    public void updateEvaluation();

    /**
     *  新增书记
     * @param book 新增图书对象
     * @return 新增图书对象
     */
    public Book createBook(Book book);

    /**
     *  更新图书
     * @param book 更新图书对象
     * @return 更新后的对象
     */
    public Book updateBook(Book book);

    /**
     *  删除图书信息, 以及该图书的评论和会员对该书的阅读状态
     * @param bookId 要删除的图书的ID
     */
    public void deleteBook(Long bookId);
}
