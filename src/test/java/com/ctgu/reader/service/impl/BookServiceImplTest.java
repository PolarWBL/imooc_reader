package com.ctgu.reader.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ctgu.reader.entity.Book;
import com.ctgu.reader.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class BookServiceImplTest {

    @Resource
    private BookService bookService;

    @Test
    public void paging() {
        IPage<Book> iPage = bookService.paging(1, 10, 1L, "quantity");
        List<Book> books = iPage.getRecords();
        for (Book book : books) {
            System.out.println(book.getBookId() + " : " + book.getBookName() + " : " + book.getEvaluationQuantity() + " : " + book.getEvaluationScore());
        }
        System.out.println("总页数: " + iPage.getPages());
        System.out.println("总数量: " + iPage.getTotal());
    }
}