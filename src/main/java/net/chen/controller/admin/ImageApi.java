package net.chen.controller.admin;

import net.chen.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Chen
 * 2020/8/1 21:25
 */
@RestController
@RequestMapping("/admin/image")
public class ImageApi {


    /** 增*/
    @PostMapping
    public ResponseResult uploadImage(){
        return null;
    }
    /** 删*/
    @DeleteMapping("/{imageId}")
    public ResponseResult deleteImage(@PathVariable("imageId") String imageId){
        return null;
    }
    /** 改*/
    @PutMapping("/{imageId}")
    public ResponseResult updateImage(@PathVariable("imageId") String imageId){
        return null;
    }
    /** 查*/
    @GetMapping("/{imageId}")
    public ResponseResult getImage(@PathVariable("imageId") String imageId){
        return null;
    }
    /** 查*/
    @GetMapping("list")
    public ResponseResult listImage(@RequestParam("page") int page,@RequestParam("size") int size){
        return null;
    }
}
