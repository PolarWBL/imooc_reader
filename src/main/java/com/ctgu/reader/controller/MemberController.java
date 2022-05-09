package com.ctgu.reader.controller;

import com.ctgu.reader.entity.Evaluation;
import com.ctgu.reader.entity.Member;
import com.ctgu.reader.entity.MemberReadState;
import com.ctgu.reader.service.MemberService;
import com.ctgu.reader.service.exception.BusinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Boliang Weng
 */
@Controller
public class MemberController {
    @Resource
    private MemberService memberService;

    @GetMapping("register.html")
    public ModelAndView showRegister(){
        return new ModelAndView("/register");
    }

    @GetMapping("login.html")
    public ModelAndView showLogin(){
        return new ModelAndView("/login");
    }

    @PostMapping("register")
    @ResponseBody
    public Map<String, Object> register(String vc, String username, String password, String nickname, HttpServletRequest request) {
        String verifyCode = (String) request.getSession().getAttribute("KaptchaVerifyCode");
        Map<String, Object> result = new HashMap<>();
        if (vc == null || "".equals(vc) || verifyCode == null) {
            result.put("code", "VC01");
            result.put("msg", "请正确输入验证码");
        } else if (vc.equals(verifyCode)) {
            try {
                memberService.createMember(username, password, nickname);
                result.put("code", "0");
                result.put("msg", "success");
            } catch (BusinessException e) {
                e.printStackTrace();
                result.put("code", e.getCode());
                result.put("msg", e.getMsg());
            }
        } else {
            result.put("code", "VC02");
            result.put("msg", "验证码错误");
        }

        return result;
    }
    @PostMapping("check_login")
    @ResponseBody
    public Map<String, Object> checkLogin(String vc, String username, String password, HttpSession session) {
        String verifyCode = (String) session.getAttribute("KaptchaVerifyCode");
        Map<String, Object> result = new HashMap<>();
        if (vc == null || "".equals(vc) || verifyCode == null) {
            result.put("code", "VC01");
            result.put("msg", "请正确输入验证码");
        } else if (vc.equals(verifyCode)) {
            try {
                Member member = memberService.checkLogin(username, password, verifyCode);
                session.setAttribute("loginMember", member);
                result.put("code", "0");
                result.put("msg", "success");
            } catch (BusinessException e) {
                e.printStackTrace();
                result.put("code", e.getCode());
                result.put("msg", e.getMsg());
            }
        } else {
            result.put("code", "VC02");
            result.put("msg", "验证码错误");
        }

        return result;
    }


    @PostMapping("update_read_state")
    @ResponseBody
    public Map<String, Object> updateReadState(Long memberId, Long bookId, Integer readState) {
        Map<String, Object> result = new HashMap<>();
        try {
            memberService.updateMemberReadState(memberId, bookId, readState);
            result.put("code", "0");
            result.put("msg", "success");
        } catch (BusinessException e) {
            e.printStackTrace();
            result.put("code", e.getCode());
            result.put("msg", e.getMsg());
        }
        return result;
    }

    @PostMapping("evaluate")
    @ResponseBody
    public Map<String, Object> evaluate(Long memberId, Long bookId, Integer score, String content) {
        Map<String, Object> result = new HashMap<>();

        try {
            Evaluation evaluation = memberService.evaluation(memberId, bookId, score, content);
            result.put("code", "0");
            result.put("msg", "success");
        } catch (BusinessException e) {
            e.printStackTrace();
            result.put("code", e.getCode());
            result.put("msg", e.getMsg());
        }
        return result;
    }

    @PostMapping("enjoy")
    @ResponseBody
    public Map<String, Object> enjoy(Long evaluationId) {
        Map<String, Object> result = new HashMap<>();

        try {
            Evaluation evaluation = memberService.enjoy(evaluationId);
            result.put("code", "0");
            result.put("msg", "success");
            result.put("evaluation", evaluation);
        } catch (BusinessException e) {
            e.printStackTrace();
            result.put("code", e.getCode());
            result.put("msg", e.getMsg());
        }
        return result;
    }

}
