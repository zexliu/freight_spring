package wiki.zex.cloud.example.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import wiki.zex.cloud.example.entity.QaDriverQuestionPager;
import wiki.zex.cloud.example.req.Pageable;
import wiki.zex.cloud.example.resp.QaDriverQuestionPagerResp;
import wiki.zex.cloud.example.resp.SimpleResp;
import wiki.zex.cloud.example.security.MyUserDetails;
import wiki.zex.cloud.example.service.IQaDriverQuestionPagerService;

import java.util.Map;

/**
 * <p>
 * 用户答题试卷 前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-06-03
 */
@RestController
@RequestMapping("/api/v1/question/pager")
@Api(tags = "试卷管理")
public class QaDriverQuestionPagerController {


    @Autowired
    private IQaDriverQuestionPagerService iQaDriverQuestionPagerService;

    @GetMapping
    public  IPage<QaDriverQuestionPager> list(Pageable pageable){
        return iQaDriverQuestionPagerService.page(pageable.convert());
    }


    @PostMapping
    @ApiOperation("获取并生成试卷信息")
    public QaDriverQuestionPagerResp generate(Authentication authentication){
       return iQaDriverQuestionPagerService.generatePager(authentication);
    }


    @PutMapping("/{id}")
    @ApiOperation("提交试卷信息")
    public SimpleResp submit(@PathVariable Long id , Authentication authentication, @RequestBody  Map<Long,String> map){
        iQaDriverQuestionPagerService.submit(id,authentication,map);
        return SimpleResp.SUCCESS;
    }
}
