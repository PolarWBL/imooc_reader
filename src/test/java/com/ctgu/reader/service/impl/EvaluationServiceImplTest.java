package com.ctgu.reader.service.impl;

import com.ctgu.reader.entity.Evaluation;
import com.ctgu.reader.service.EvaluationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class EvaluationServiceImplTest {
    @Resource
    private EvaluationService evaluationService;

    @Test
    public void selectByBookId() {
        List<Evaluation> evaluations = evaluationService.selectByBookId(1L);
        for (Evaluation evaluation : evaluations) {
            System.out.println(evaluation.getEvaluationId() + " : " + evaluation.getContent());
        }
    }
}