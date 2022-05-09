package com.ctgu.reader.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ctgu.reader.entity.Book;

/**
 * @author Boliang Weng
 */
public interface BookMapper extends BaseMapper<Book> {
    /**
     * 更新所有图书评分和评价数量信息
     */
    public void updateEvaluation();
}
