package com.ctgu.reader.controller;

import com.google.code.kaptcha.Producer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author Boliang Weng
 */
@Controller
public class KaptchaController {
    @Resource
    private Producer kaptchaProducer;

    @GetMapping("verify_code")
    public void createVerifyCode(HttpServletRequest request, HttpServletResponse response) {
        //响应立即过期
        response.setDateHeader("Expires", 0);
        //不缓存任何图片数据
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.setHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/png");
        //生成验证码字符文本
        String verifyCode = kaptchaProducer.createText();
        request.getSession().setAttribute("KaptchaVerifyCode", verifyCode);
        System.out.println(request.getSession().getAttribute("KaptchaVerifyCode"));
        BufferedImage image = kaptchaProducer.createImage(verifyCode);
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            ImageIO.write(image, "png", outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
