package com.ctgu.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ctgu.reader.entity.Category;
import com.ctgu.reader.mapper.CategoryMapper;
import com.ctgu.reader.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 使用@Transactional关闭事务控制
 * @author Boliang Weng
 */
@Service("categoryService")
@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 查询所有图书分类
     * @return 返回图书分类列表
     */
    @Override
    public List<Category> selectAll() {
        return categoryMapper.selectList(new QueryWrapper<>());
    }
}
