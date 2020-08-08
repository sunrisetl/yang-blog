package net.chen.controller.admin;

import lombok.extern.slf4j.Slf4j;
import net.chen.entity.Category;
import net.chen.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Chen
 * 2020/8/1 20:40
 */
@Slf4j
@RestController
@RequestMapping("/admin/category")
public class CategoryAdminApi {

    /** 添加接口方法*/

    /** 添加分类*/
    @PostMapping()
    public ResponseResult addCategory(@RequestBody Category category){

        return null;

    }
    /** 删除分类*/
    @DeleteMapping("/{categoryId}")
    public ResponseResult deleteCategory(@PathVariable("categoryId") String categoryId){

        return null;

    }
    /** 修改分类*/
    @PutMapping("/{categoryId}")
    public ResponseResult updateCategory(@PathVariable("categoryId") String categoryId,@RequestBody Category category){

        return null;

    }
    /** 查询分类*/
    @GetMapping("/{categoryId}")
    public ResponseResult getCategory(@PathVariable("categoryId") String categoryId){
        return null;
    }

    /** 获取分类列表*/
    @GetMapping("/list")
    public ResponseResult listCategories(@RequestParam("page") int page,@RequestParam("size") int size){
        return null;
    }
}
