package net.chen.controller.admin;

import net.chen.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Chen
 * 2020/8/1 21:25
 */
@RestController
@RequestMapping("/admin/comment")
public class CommentApi {

    /** 删*/
    @DeleteMapping("/{commentId}")
    public ResponseResult deleteComment(@PathVariable("commentId") String commentId){
        return null;
    }

    /** 查*/
    @GetMapping("list")
    public ResponseResult listComment(@RequestParam("page") int page,@RequestParam("size") int size){
        return null;
    }

    /** 置顶*/
    @PutMapping("/top/{commentId}")
    public ResponseResult topComment(@PathVariable("commentId") String commentId){
        return null;

    }
}
