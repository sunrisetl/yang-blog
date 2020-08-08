package net.chen.controller.admin;

import net.chen.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Chen
 * 2020/8/1 21:25
 */
@RestController
@RequestMapping("/admin/web_size")
public class WebInfoApi {

    @GetMapping("/title")
    public ResponseResult getWebSizeTitle(){
        return null;
    }
    @PutMapping("/titleId")
    public ResponseResult updateWebSizeTitle(@RequestParam("title") String title){
        return null;
    }
    @GetMapping("/seo")
    public ResponseResult getSeoInfo(){
        return null;
    }
    @PutMapping("/seo")
    public ResponseResult updateSeoInfo(@RequestParam("keywords") String keywords,@RequestParam("description") String description){
        return null;
    }

    /** 获取统计信息*/
    @GetMapping("/view_count")
    public ResponseResult getWebSizeViewCount(){
        return null;
    }
}
