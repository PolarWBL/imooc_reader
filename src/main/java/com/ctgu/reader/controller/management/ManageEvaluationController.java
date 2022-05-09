package com.ctgu.reader.controller.management;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ctgu.reader.entity.Book;
import com.ctgu.reader.entity.Evaluation;
import com.ctgu.reader.mapper.EvaluationMapper;
import com.ctgu.reader.service.BookService;
import com.ctgu.reader.service.EvaluationService;
import com.ctgu.reader.service.MemberService;
import com.ctgu.reader.service.exception.BusinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Boliang Weng
 */
@Controller
@RequestMapping("management/evaluation")
public class ManageEvaluationController {
    @Resource
    private EvaluationService evaluationService;
    @Resource
    private BookService bookService;
    @Resource
    private MemberService memberService;

    @GetMapping("index.html")
    public ModelAndView showIndex() {
        return new ModelAndView("/management/evaluation");
    }

    @GetMapping("list")
    @ResponseBody
    public Map<String, Object> list(Integer page, Integer limit) {
        //要返回的 Map数据
        Map<String, Object> result = new HashMap<>();
        //判断传入的数据
        if (page == null) {
            page = 1;
        }
        if (limit == null) {
            limit = 20;
        }
        try {
            IPage<Evaluation> iPage = evaluationService.paging(page, limit);
            Long bookId;
            Long memberId;
            for (Evaluation evaluation : iPage.getRecords()) {
                bookId = evaluation.getBookId();
                memberId = evaluation.getMemberId();
                evaluation.setBook(bookService.selectById(bookId));
                evaluation.setMember(memberService.selectById(memberId));
            }
            result.put("code", 0);
            result.put("msg", "success");
            result.put("data", iPage.getRecords());
            result.put("count", iPage.getTotal());
        } catch (BusinessException e) {
            e.printStackTrace();
            result.put("code", e.getCode());
            result.put("msg", e.getMsg());
        }
        return result;
    }

    @PostMapping("disable")
    @ResponseBody
    public Map<String, Object> disable(Long evaluationId, String reason){
        //要返回的 Map数据
        Map<String, Object> result = new HashMap<>();
        try {
            Evaluation evaluation = evaluationService.selectById(evaluationId);
            evaluation.setState("disable");
            evaluation.setDisableReason(reason);
            evaluationService.update(evaluation);
            result.put("code", 0);
            result.put("msg", "success");
        } catch (BusinessException e) {
            e.printStackTrace();
            result.put("code", e.getCode());
            result.put("msg", e.getMsg());
        }
        return result;
    }

}
