package wiki.zex.cloud.example.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import wiki.zex.cloud.example.req.Pageable;
import wiki.zex.cloud.example.resp.QaDriverPagerAnswerResp;
import wiki.zex.cloud.example.service.IQaDriverPagerAnswerService;

/**
 * <p>
 * 司机答题表 前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-06-03
 */
@RestController
@RequestMapping("/api/v1/question/answer")
@Api(tags = "司机答案相关")
public class QaDriverPagerAnswerController {

    @Autowired
    private IQaDriverPagerAnswerService iQaDriverPagerAnswerService;

    @GetMapping
    public IPage<QaDriverPagerAnswerResp> page(Pageable pageable){
        return iQaDriverPagerAnswerService.respPage(pageable.convert());
    }

}
