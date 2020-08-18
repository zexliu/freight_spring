package wiki.zex.cloud.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import wiki.zex.cloud.example.entity.QaQuestionPagerTemplate;
import wiki.zex.cloud.example.req.Pageable;
import wiki.zex.cloud.example.req.QaQuestionPagerTemplateReq;
import wiki.zex.cloud.example.resp.SimpleResp;
import wiki.zex.cloud.example.service.IQaQuestionPagerTemplateService;

import javax.validation.Valid;

/**
 * <p>
 * 试卷模板 前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-06-03
 */
@RestController
@RequestMapping("/api/v1/question/templates")
@Api(tags = "试题模板")
public class QaQuestionPagerTemplateController {


    @Autowired
    private IQaQuestionPagerTemplateService iQaQuestionPagerTemplateService;

    @GetMapping
    public IPage<QaQuestionPagerTemplate> list(Pageable pageable) {
        return iQaQuestionPagerTemplateService.page(pageable.convert(),new LambdaQueryWrapper<QaQuestionPagerTemplate>()
        .orderByDesc(QaQuestionPagerTemplate::getEnable));
    }


    @PostMapping
    public QaQuestionPagerTemplate create(@Valid @RequestBody QaQuestionPagerTemplateReq req){
        return iQaQuestionPagerTemplateService.create(req);
    }
    @PutMapping("/{id}")
    public QaQuestionPagerTemplate update(@PathVariable Long id,@Valid @RequestBody QaQuestionPagerTemplateReq req){
        return iQaQuestionPagerTemplateService.update(id,req);
    }

    @PutMapping("/{id}/enable")
    @ApiOperation("设置启用模板")
    public SimpleResp enable(@PathVariable Long id){
         iQaQuestionPagerTemplateService.enable(id);
        return SimpleResp.SUCCESS;

    }

    @DeleteMapping("/{id}")
    public SimpleResp delete(@PathVariable Long id){
         iQaQuestionPagerTemplateService.delete(id);
        return SimpleResp.SUCCESS;
    }
}
