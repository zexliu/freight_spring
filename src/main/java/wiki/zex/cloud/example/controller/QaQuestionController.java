package wiki.zex.cloud.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import wiki.zex.cloud.example.entity.QaQuestion;
import wiki.zex.cloud.example.entity.QaQuestionStore;
import wiki.zex.cloud.example.req.Pageable;
import wiki.zex.cloud.example.req.QaQuestionReq;
import wiki.zex.cloud.example.req.QaQuestionStoreReq;
import wiki.zex.cloud.example.resp.SimpleResp;
import wiki.zex.cloud.example.service.IQaQuestionService;
import wiki.zex.cloud.example.service.IQaQuestionStoreService;

import javax.validation.Valid;

/**
 * <p>
 * 试题 前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-06-03
 */
@RestController
@RequestMapping("/api/v1/questions")
@Api(tags = "问题相关")
public class QaQuestionController {

    @Autowired
    private IQaQuestionService iQaQuestionService;

    @GetMapping("/{id}")
    public QaQuestion getById(@PathVariable Long id){
        return iQaQuestionService.getById(id);

    }


    @GetMapping
    public IPage<QaQuestion> list(Pageable pageable, Long questionStoreId,String questionContent, Integer questionType) {
        return iQaQuestionService.page(pageable.convert(), new LambdaQueryWrapper<QaQuestion>()
                .eq(questionStoreId != null, QaQuestion::getQuestionStoreId, questionStoreId)
                .eq(questionType != null, QaQuestion::getQuestionType, questionType)
        .like(StringUtils.isNotBlank(questionContent),QaQuestion::getQuestionContent,questionContent));
    }

    @PostMapping
    public QaQuestion create(@RequestBody @Valid QaQuestionReq req){
        return iQaQuestionService.create(req);

    }

    @PutMapping("/{id}")
    public QaQuestion update(@PathVariable Long id ,@RequestBody @Valid QaQuestionReq req){
        return iQaQuestionService.update(id,req);

    }

    @DeleteMapping("/{id}")
    public SimpleResp delete(@PathVariable Long id ){
        iQaQuestionService.removeById(id);
        return SimpleResp.SUCCESS;
    }

}
