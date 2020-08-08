package net.chen.controller.portal;

import net.chen.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Chen
 * 2020/8/2 15:33
 */
@RestController
@RequestMapping("/portal/article")
public class ArticlePortalApi {

    @GetMapping("/list/{page}/{size}")
    public ResponseResult listArticle(@PathVariable("page") int page, @PathVariable("size")int size){
        return null;
    }

    @GetMapping("/list/{categoryId}/{page}/{size}")
    public ResponseResult listArticleCategoryId (@PathVariable("page") int page,
                                                 @PathVariable("categoryId") String categoryId,
                                                 @PathVariable("size")int size){
        return null;
    }

    /** 获取文章详情*/
    @GetMapping("/{articleId}")
    public ResponseResult getArticleDetail(@PathVariable("articleId") String articleId){
        return null;
    }

    /** 获取推荐文章*/
    @GetMapping("/recommend/{articleId}")
    public ResponseResult getRecommendArticles(@PathVariable("articleId") String articleId){
        return null;
    }


}
