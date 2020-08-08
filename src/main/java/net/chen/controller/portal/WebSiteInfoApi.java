package net.chen.controller.portal;

import net.chen.response.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Chen
 * 2020/8/2 15:45
 */
@RestController
@RequestMapping("/portal/web_size")
public class WebSiteInfoApi {

    @GetMapping("/categories")
    public ResponseResult getCategories(){
            return null;
    }

    @GetMapping("/title")
    public ResponseResult getWebSiteTitle(){
        return null;
    }
    @GetMapping("/view_count")
    public ResponseResult getWebSiteCount(){
        return null;
    }
    @GetMapping("/seo")
    public ResponseResult getWebSiteSeoInfo(){
        return null;
    }

    @GetMapping("/loops")
    public ResponseResult getLoops(){
        return null;
    }
    @GetMapping("/friend_link")
    public ResponseResult getLinks(){
        return null;
    }
}
