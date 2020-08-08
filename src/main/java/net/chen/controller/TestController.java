package net.chen.controller;

import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import net.chen.dao.LabelDao;
import net.chen.entity.Labels;
import net.chen.response.ResponseResult;
import net.chen.response.ResponseState;
import net.chen.utils.Constants;
import net.chen.utils.RedisUtil;
import org.apache.juli.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Chen
 * 2020/7/28 23:14
 */
@Controller
public class TestController {
    public static final Logger log = LoggerFactory.getLogger(TestController.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private LabelDao labelDao;

    @GetMapping("/test")
    @ResponseBody
    public ResponseResult hello(){
        log.info("nihao");
        String captcha = (String) redisUtil.get(Constants.User.CAPTCHA_CONTENT+"1234");
        log.info("-----=>"+captcha);
        //ResponseResult responseResult = ResponseResult.SUCCESS();

        return new ResponseResult(ResponseState.LOGIN_FAILED);
    }

    @GetMapping("/query")
    @ResponseBody
    public Map<String,Object> map(){
      //  ResponseResult responseResult = new ResponseResult();
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from  tb_blogs"  );
        return maps.get(0);
    }

    /** 查询数据*/
    @GetMapping("/label/{labelId}")
    public ResponseResult getLabelById(@PathVariable("labelId") String labelId){
        Labels labels = labelDao.findOneById(labelId);
        if (labels == null){
            return ResponseResult.FAILED("标签不存在");
        }
        else {
            return ResponseResult.SUCCESS("查询成功").setData(labels);
        }
    }

    /** 分页查询*/
    @GetMapping("/label/list/{page}/{size}")
    public ResponseResult listLabels(@PathVariable("page") int page,@PathVariable("size") int size){
        if (page < 1){
            page = 1;
        }
        if (size <= 0){
            size = Constants.DEFAULT_SIZE;
        }
        //排序
        Sort sort = new Sort(Sort.Direction.DESC,"create_time");
        Pageable pageable = PageRequest.of(page-1,size);
        Page<Labels> result = labelDao.findAll(pageable);
        return ResponseResult.SUCCESS("查询成功").setData(result);
    }
    @GetMapping("/label/search")
    public ResponseResult LabelSearch(@RequestParam("keyword") String keyword){

        List<Labels> all = labelDao.findAll(new Specification<Labels>() {
            @Override
            public Predicate toPredicate(Root<Labels> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Predicate predicate = cb.like(root.get("name").as(String.class),"%" + keyword + "%");
                return predicate;
            }
        });
        if (all == null || all.size() == 0){
            return ResponseResult.FAILED("数据不存在!!");
        }
        return  ResponseResult.SUCCESS("查找成功").setData(all);
    }


    @Autowired
    private RedisUtil redisUtil;
    /**图灵验证*/
    @RequestMapping("/captcha")
    @ResponseBody
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
            response.setContentType("image/gif");
            response.setHeader("Pragma","No-cache");
            response.setHeader("Cache-Control","no-cache");
            response.setDateHeader("Expires",0);

            // 三个参数分别为宽、高、位数
            SpecCaptcha specCaptcha = new SpecCaptcha(130,48,5);
           // specCaptcha.setFont(new Font("Verdana",Font.PLAIN,32));
            //设置类型，字母数字混合
            specCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);

        String content = specCaptcha.text().toLowerCase();
        log.info("====>"+content);
        //将验证码存入session
            //request.getSession().setAttribute("captcha",specCaptcha.text().toLowerCase());\
            //存入redis
            redisUtil.set(Constants.User.CAPTCHA_CONTENT+"1234",content,60*10);
            //输出
            specCaptcha.out(response.getOutputStream());

    }

}
