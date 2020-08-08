package net.chen.controller.portal;

import net.chen.response.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Chen
 * 2020/8/2 15:50
 */
@RestController
@RequestMapping("/portal/search")
public class SearchPortalApi {

    @GetMapping("")
    public ResponseResult doSearch(@RequestParam("keyword") String keyword,@RequestParam("page")int page,@RequestParam("size")int size){

        return null;
    }
}
