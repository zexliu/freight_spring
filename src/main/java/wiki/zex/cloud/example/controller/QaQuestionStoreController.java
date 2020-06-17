package wiki.zex.cloud.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import wiki.zex.cloud.example.entity.QaQuestionStore;
import wiki.zex.cloud.example.req.Pageable;
import wiki.zex.cloud.example.req.QaQuestionStoreReq;
import wiki.zex.cloud.example.resp.SimpleResp;
import wiki.zex.cloud.example.service.IQaQuestionStoreService;

import javax.validation.Valid;

/**
 * <p>
 * 试题题库 前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-06-03
 */
@RestController
@RequestMapping("/api/v1/question/stories")
@Api(tags = "题库相关")
public class QaQuestionStoreController {

    @Autowired
    private IQaQuestionStoreService iQaQuestionStoreService;

    @GetMapping
    public IPage<QaQuestionStore> list(Pageable pageable,String questionStoreName){
        return iQaQuestionStoreService.page(pageable.convert(),new LambdaQueryWrapper<QaQuestionStore>().like(StringUtils.isNotBlank(questionStoreName),QaQuestionStore::getQuestionStoreName,questionStoreName));
    }

    @PostMapping
    public QaQuestionStore create(@RequestBody @Valid QaQuestionStoreReq req){
        return iQaQuestionStoreService.create(req);

    }

    @GetMapping("/{id}")
    public QaQuestionStore getById(@PathVariable Long id){
        return iQaQuestionStoreService.getById(id);

    }

    @PutMapping("/{id}")
    public QaQuestionStore update(@PathVariable Long id ,@RequestBody @Valid QaQuestionStoreReq req){
        return iQaQuestionStoreService.update(id,req);

    }

    @DeleteMapping("/{id}")
    public SimpleResp delete(@PathVariable Long id ){
         iQaQuestionStoreService.removeById(id);
        return SimpleResp.SUCCESS;
    }



}
