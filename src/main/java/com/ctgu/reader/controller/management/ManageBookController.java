package com.ctgu.reader.controller.management;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ctgu.reader.entity.Book;
import com.ctgu.reader.service.BookService;
import com.ctgu.reader.service.exception.BusinessException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Boliang Weng
 */
@Controller
@RequestMapping("management/book")
public class ManageBookController {

    @Resource
    private BookService bookService;

    @GetMapping("index.html")
    public ModelAndView showBook() {
        return new ModelAndView("/management/book");
    }

    @PostMapping("upload")
    @ResponseBody
    public Map<String, Object> upload(MultipartFile imageFile, HttpServletRequest request) throws IOException {
        //要返回的 Map数据
        Map<String, Object> result = new HashMap<>();
        //得到文件要上传的目录
        String uploadPath = request.getServletContext().getRealPath("/upload") + "\\";
        // 新建文件名
        String fileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        //获取文件后缀
        String originalFilename = imageFile.getOriginalFilename();
        String suffix = null;
        if (originalFilename != null) {
            suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        if (suffix == null) {
            throw new BusinessException("UPLOAD01", "图片上传失败");
        }
        //保存文件
        String pathName = uploadPath + fileName + suffix;
        System.out.println("上传的文件地址: [" + pathName + "]");
        imageFile.transferTo(new File(pathName));
        result.put("errno", 0);
        result.put("data", new String[]{"/upload/" + fileName + suffix});
        return result;
    }

    @PostMapping("create")
    @ResponseBody
    public Map<String, Object> create(Book book) {
        //要返回的 Map数据
        Map<String, Object> result = new HashMap<>();
        try {
            //通过Jsoup分析器获取html文本中的图片链接
            Document document = Jsoup.parse(book.getDescription());
            Element img = document.select("img").first();
            if (img == null) {
                throw new BusinessException("CREATE01", "封面图片不存在");
            }
            book.setCover(img.attr("src"));
            book.setEvaluationScore(0F);
            book.setEvaluationQuantity(0);
            bookService.createBook(book);
            result.put("code", 0);
            result.put("msg", "success");
        } catch (BusinessException e) {
            e.printStackTrace();
            result.put("code", e.getCode());
            result.put("msg", e.getMsg());
        }
        return result;
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
            limit = 10;
        }
        try {
            IPage<Book> iPage = bookService.paging(page, limit, null, null);
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

    @GetMapping("id/{id}")
    @ResponseBody
    public Map<String, Object> selectById(@PathVariable Long id){
        //要返回的 Map数据
        Map<String, Object> result = new HashMap<>();
        //查询 book数据
        try {
            Book book = bookService.selectById(id);
            result.put("code", 0);
            result.put("msg", "success");
            result.put("data", book);
        } catch (BusinessException e) {
            e.printStackTrace();
            result.put("code", e.getCode());
            result.put("msg", e.getMsg());
        }
        return result;
    }

    @PostMapping("update")
    @ResponseBody
    public Map<String, Object> updateBook(Book book) {
        //要返回的 Map数据
        Map<String, Object> result = new HashMap<>();
        try {
            Book oldBook = bookService.selectById(book.getBookId());
            oldBook.setCategoryId(book.getCategoryId());
            oldBook.setBookName(book.getBookName());
            oldBook.setSubTitle(book.getSubTitle());
            oldBook.setAuthor(book.getAuthor());
            oldBook.setDescription(book.getDescription());
            //通过Jsoup分析器获取html文本中的图片
            Element img = Jsoup.parse(book.getDescription()).select("img").first();
            if (img == null) {
                throw new BusinessException("CREATE01", "封面图片不存在");
            }
            oldBook.setCover(img.attr("src"));
            bookService.updateBook(oldBook);
            result.put("code", 0);
            result.put("msg", "success");
        } catch (BusinessException e) {
            e.printStackTrace();
            result.put("code", e.getCode());
            result.put("msg", e.getMsg());
        }
        return result;
    }

    @GetMapping("delete/{id}")
    @ResponseBody
    public Map<String, Object> deleteBook(@PathVariable Long id){
        //要返回的 Map数据
        Map<String, Object> result = new HashMap<>();
        try {
            bookService.deleteBook(id);
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
