package net.chen.controller.admin;

import net.chen.entity.Article;
import net.chen.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Chen
 * 2020/8/1 21:25
 */
@RestController
@RequestMapping("/admin/article")
public class ArticleApi {

    /** 增*/
    @PostMapping
    public ResponseResult addArticle(@RequestBody Article article){
        return null;
    }
    /** 删*/
    @DeleteMapping("/{articleId}")
    public ResponseResult deleteArticle(@PathVariable("articleId") String articleId){
        return null;
    }
    /** 改*/
    @PutMapping("/{articleId}")
    public ResponseResult updateArticle(@PathVariable("articleId") String articleId){
        return null;
    }
    /** 查*/
    @GetMapping("/{articleId}")
    public ResponseResult getArticle(@PathVariable("articleId") String articleId){
        return null;
    }
    /** 查*/
    @GetMapping("list")
    public ResponseResult listArticle(@RequestParam("page") int page,@RequestParam("size") int size){
        return null;
    }

    @PutMapping("/sate/{articleId}/{state}")
    public ResponseResult updateArticleState(@PathVariable("articleId") String articleId ,@PathVariable("state") String state){
        return null;
    }

    /** 置顶*/
    @PutMapping("/top/{articleId}/{state}")
    public ResponseResult updateArticleState(@PathVariable("articleId") String articleId){
        return null;
    }
}
