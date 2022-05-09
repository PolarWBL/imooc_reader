package com.ctgu.reader.task;

import com.ctgu.reader.service.BookService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Boliang Weng
 */
@Component
public class ComputeTask {
    @Resource
    private BookService bookService;

    @Scheduled(cron = "0 * * * * ?")
    public void updateEvaluation(){
        bookService.updateEvaluation();
        System.out.println("==========已更新所有图书的评分信息==========");
    }
}
