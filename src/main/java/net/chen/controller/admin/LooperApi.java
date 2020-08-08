package net.chen.controller.admin;

import net.chen.entity.Looper;
import net.chen.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Chen
 * 2020/8/1 21:25
 */
@RestController
@RequestMapping("/admin/looper")
public class LooperApi {

    /** 增*/
    @PostMapping
    public ResponseResult addLoop(@RequestBody Looper looper){
        return null;
    }
    /** 删*/
    @DeleteMapping("/{LoopId}")
    public ResponseResult deleteLoop(@PathVariable("LoopId") String LoopId){
        return null;
    }
    /** 改*/
    @PutMapping("/{LoopId}")
    public ResponseResult updateLoop(@PathVariable("LoopId") String LoopId){
        return null;
    }
    /** 查*/
    @GetMapping("/{LoopId}")
    public ResponseResult getLoop(@PathVariable("LoopId") String LoopId){
        return null;
    }
    /** 查*/
    @GetMapping("list")
    public ResponseResult listLoop(@RequestParam("page") int page,@RequestParam("size") int size){
        return null;
    }

}
