package com.ctgu.reader.service;

import com.ctgu.reader.entity.Category;

import java.util.List;

/**
 * 图书分类服务
 * @author Boliang Weng
 */
public interface CategoryService {
    /**
     * 查询所有图书分类
     * @return 返回图书分类列表
     */
    public List<Category> selectAll();
}
