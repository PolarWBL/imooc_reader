package com.ctgu.reader.service.impl;

import com.ctgu.reader.entity.Member;
import com.ctgu.reader.entity.MemberReadState;
import com.ctgu.reader.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class MemberServiceImplTest {

    @Resource
    private MemberService memberService;

    @Test
    public void createMember() {
        Member member = memberService.createMember("test1", "123456", "test1");
        System.out.println(member.getUsername() + " : " + member.getNickname());
    }

    @Test
    public void selectMemberReadState() {
        MemberReadState memberReadState = memberService.selectMemberReadState(1L, 2L);
        if (memberReadState != null) {
            System.out.println("阅读状态为: [" + memberReadState.getReadState() + "] ");
        }

    }
}