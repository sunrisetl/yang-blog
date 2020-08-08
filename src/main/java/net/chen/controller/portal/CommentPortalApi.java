package net.chen.controller.portal;

import net.chen.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Chen
 * 2020/8/1 22:09
 */
@RestController
@RequestMapping("/portal/comment")
public class CommentPortalApi {
    /** 增*/
    @PostMapping
    public ResponseResult postComment(){
        return null;
    }
    /** 删*/
    @DeleteMapping("/{commentId}")
    public ResponseResult deleteComment(@PathVariable("commentId") String commentId){
        return null;
    }
    /** 获取评论列表*/
    @GetMapping("/{commentId}")
    public ResponseResult listComment(@PathVariable("commentId") String commentId){
        return null;
    }





}
