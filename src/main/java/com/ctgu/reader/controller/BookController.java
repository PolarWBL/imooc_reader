package com.ctgu.reader.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ctgu.reader.entity.*;
import com.ctgu.reader.service.BookService;
import com.ctgu.reader.service.CategoryService;
import com.ctgu.reader.service.EvaluationService;
import com.ctgu.reader.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Boliang Weng
 */
@Controller
public class BookController {
    @Resource
    private CategoryService categoryService;
    @Resource
    private BookService bookService;
    @Resource
    private EvaluationService evaluationService;
    @Resource
    private MemberService memberService;

    @GetMapping("/")
    public ModelAndView showIndex() {
        ModelAndView modelAndView = new ModelAndView("/index");
        List<Category> categoryList = categoryService.selectAll();
        modelAndView.addObject("categoryList", categoryList);
        return modelAndView;
    }

    @GetMapping("books")
    @ResponseBody
    public IPage<Book> selectBook(Integer p, Long categoryId, String order) {
        if (p == null) {
            p = 1;
        }
        return bookService.paging(p, 10, categoryId, order);
    }

    @GetMapping("book/{id}")
    public ModelAndView showDetail(@PathVariable("id") Long bookId, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("/detail");
        Book book = bookService.selectById(bookId);
        List<Evaluation> evaluationList = evaluationService.selectByBookId(bookId);

        Member member = (Member) session.getAttribute("loginMember");
        if (member != null) {
            MemberReadState memberReadState = memberService.selectMemberReadState(member.getMemberId(), bookId);
            modelAndView.addObject("memberReadState", memberReadState);
        }
        modelAndView.addObject("book", book);
        modelAndView.addObject("evaluations", evaluationList);
        return modelAndView;
    }
}
