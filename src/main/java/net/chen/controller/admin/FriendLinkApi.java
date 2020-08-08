package net.chen.controller.admin;

import net.chen.entity.FriendLink;
import net.chen.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Chen
 * 2020/8/1 21:25
 */
@RestController
@RequestMapping("/admin/friend_link")
public class FriendLinkApi {

    /** 增*/
    @PostMapping
    public ResponseResult addFriendLink(@RequestBody FriendLink friendLink){
        return null;
    }
    /** 删*/
    @DeleteMapping("/{friendLinkId}")
    public ResponseResult deleteFriendLink(@PathVariable("friendLinkId") String friendLinkId){
        return null;
    }
    /** 改*/
    @PutMapping("/{friendLinkId}")
    public ResponseResult updateFriendLink(@PathVariable("friendLinkId") String friendLinkId){
        return null;
    }
    /** 查*/
    @GetMapping("/{friendLinkId}")
    public ResponseResult getFriendLink(@PathVariable("friendLinkId") String friendLinkId){
        return null;
    }
    /** 查*/
    @GetMapping("list")
    public ResponseResult listFriendLinks(@RequestParam("page") int page,@RequestParam("size") int size){
        return null;
    }

}
