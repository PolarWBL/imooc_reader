package com.ctgu.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ctgu.reader.entity.Evaluation;
import com.ctgu.reader.entity.Member;
import com.ctgu.reader.entity.MemberReadState;
import com.ctgu.reader.mapper.EvaluationMapper;
import com.ctgu.reader.mapper.MemberMapper;
import com.ctgu.reader.mapper.MemberReadStateMapper;
import com.ctgu.reader.service.MemberService;
import com.ctgu.reader.service.exception.BusinessException;
import com.ctgu.reader.utils.Md5Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author Boliang Weng
 */
@Service("memberService")
@Transactional(rollbackFor = RuntimeException.class)
public class MemberServiceImpl implements MemberService {
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private MemberReadStateMapper memberReadStateMapper;
    @Resource
    private EvaluationMapper evaluationMapper;

    /**
     * 会员注册
     *
     * @param username 会员用户名
     * @param password 会员密码
     * @param nickname 会员昵称
     * @return 返回新会员对象
     */
    @Override
    public Member createMember(String username, String password, String nickname) {
        //判断用户名是否存在
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        List<Member> memberList = memberMapper.selectList(wrapper);
        if (memberList.size() > 0) {
            throw new BusinessException("M01", "用户名已存在");
        }
        int salt = new Random().nextInt(1000) + 1000;
        String md5Digest = Md5Utils.getMd5Digest(password, salt);

        Member member = new Member();
        member.setUsername(username);
        member.setPassword(md5Digest);
        member.setSalt(salt);
        member.setNickname(nickname);
        member.setCreateTime(new Date());

        memberMapper.insert(member);

        return member;
    }

    /**
     * 用户登录检验
     *
     * @param username   用户名
     * @param password   密码
     * @param verifyCode 验证码
     * @return 返回登录的用户
     */
    @Override
    public Member checkLogin(String username, String password, String verifyCode) {
        //判断用户名是否存在
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        Member member = memberMapper.selectOne(wrapper);

        if (member == null) {
            throw new BusinessException("M02", "用户名不存在");
        }

        Integer salt = member.getSalt();
        String md5Digest = Md5Utils.getMd5Digest(password, salt);

        if (!member.getPassword().equals(md5Digest)) {
            throw new BusinessException("M03", "密码错误");
        }

        return member;
    }

    /**
     * 会员图书阅读情况
     *
     * @param memberId 会员ID
     * @param bookId   图书ID
     * @return 返回阅读状态
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public MemberReadState selectMemberReadState(Long memberId, Long bookId) {
        QueryWrapper<MemberReadState> wrapper = new QueryWrapper<>();
        wrapper.eq("book_id", bookId);
        wrapper.eq("member_id", memberId);
        return memberReadStateMapper.selectOne(wrapper);
    }

    /**
     * 更新会员图书阅读情况
     *
     * @param memberId 会员ID
     * @param bookId   图书ID
     * @param state    阅读状态
     */
    @Override
    public void updateMemberReadState(Long memberId, Long bookId, Integer state) {
        QueryWrapper<MemberReadState> wrapper = new QueryWrapper<>();
        wrapper.eq("book_id", bookId);
        wrapper.eq("member_id", memberId);
        MemberReadState memberReadState = memberReadStateMapper.selectOne(wrapper);
        System.out.println("要更新的状态为: [" + state + "]");
        if (memberReadState == null) {
            memberReadState = new MemberReadState();
            memberReadState.setBookId(bookId);
            memberReadState.setMemberId(memberId);
            memberReadState.setReadState(state);
            memberReadState.setCreateTime(new Date());
            memberReadStateMapper.insert(memberReadState);
        }else {
            memberReadState.setReadState(state);
            memberReadStateMapper.updateById(memberReadState);
        }

    }

    /**
     * 为图书添加短评
     *
     * @param memberId 会员ID
     * @param bookId   图书ID
     * @param score    分数
     * @param content  内容
     * @return 返回短评
     */
    @Override
    public Evaluation evaluation(Long memberId, Long bookId, Integer score, String content) {
        Evaluation evaluation = new Evaluation();
        evaluation.setMemberId(memberId);
        evaluation.setBookId(bookId);
        evaluation.setScore(score);
        evaluation.setContent(content);
        evaluation.setCreateTime(new Date());
        evaluation.setState("enable");
        evaluation.setEnjoy(0);
        evaluationMapper.insert(evaluation);
        return evaluation;
    }

    /**
     * 为短评点赞
     *
     * @param evaluationId 要点赞的短评id
     * @return 返回短评
     */
    @Override
    public Evaluation enjoy(Long evaluationId) {
        Evaluation evaluation = evaluationMapper.selectById(evaluationId);
        evaluation.setEnjoy(evaluation.getEnjoy() + 1);
        evaluationMapper.updateById(evaluation);
        return evaluation;
    }

    /**
     * 通过id 查询 会员信息
     *
     * @param memberId 会员id
     * @return 返回会员实体类
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public Member selectById(Long memberId) {
        return memberMapper.selectById(memberId);
    }


}



















