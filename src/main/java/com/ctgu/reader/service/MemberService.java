package com.ctgu.reader.service;

import com.ctgu.reader.entity.Evaluation;
import com.ctgu.reader.entity.Member;
import com.ctgu.reader.entity.MemberReadState;

import javax.swing.*;

/**
 * @author Boliang Weng
 */
public interface MemberService {

    /**
     *  会员注册
     *
     * @param username 会员用户名
     * @param password 会员密码
     * @param nickname 会员昵称
     * @return 返回新会员对象
     */
    public Member createMember(String username, String password, String nickname);

    /**
     * 用户登录检验
     *
     * @param username 用户名
     * @param password 密码
     * @param verifyCode 验证码
     * @return 返回登录的用户
     */
    public Member checkLogin(String username, String password, String verifyCode);

    /**
     *  会员图书阅读情况
     * @param memberId 会员ID
     * @param bookId 图书ID
     * @return 返回阅读状态
     */
    public MemberReadState selectMemberReadState(Long memberId, Long bookId);

    /**
     *  更新会员图书阅读情况
     * @param memberId 会员ID
     * @param bookId 图书ID
     * @param state 阅读状态
     */
    public void updateMemberReadState(Long memberId, Long bookId, Integer state);

    /**
     *  为图书添加短评
     * @param memberId 会员ID
     * @param bookId 图书ID
     * @param score 分数
     * @param content 内容
     * @return 返回短评
     */
    public Evaluation evaluation(Long memberId, Long bookId, Integer score, String content);

    /**
     *  为短评点赞
     * @param evaluationId 要点赞的短评id
     * @return 返回短评
     */
    public Evaluation enjoy(Long evaluationId);


    /**
     *  通过id 查询 会员信息
     * @param memberId 会员id
     * @return 返回会员实体类
     */
    public Member selectById(Long memberId);
}
