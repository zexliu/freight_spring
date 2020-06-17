package wiki.zex.cloud.example.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import wiki.zex.cloud.example.entity.SbRuntimeTable;
import wiki.zex.cloud.example.req.Pageable;
import wiki.zex.cloud.example.req.SbRuntimeTableReq;
import wiki.zex.cloud.example.resp.SimpleResp;
import wiki.zex.cloud.example.service.ISbRuntimeTableService;

import javax.validation.Valid;

/**
 * <p>
 * 时刻表 前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-06-15
 */
@RestController
@RequestMapping("/api/v1/runtime/tables")
@Api(tags = "时刻表管理")
public class SbRuntimeTableController {

    @Autowired
    private ISbRuntimeTableService iSbRuntimeTableService;

    @GetMapping
    public IPage<SbRuntimeTable> list(Pageable pageable){
        return iSbRuntimeTableService.page(pageable.convert());
    }

    @PostMapping
    public SbRuntimeTable  create(@Valid @RequestBody SbRuntimeTableReq req){
        return iSbRuntimeTableService.create(req);
    }

    @GetMapping("/{id}")
    public SbRuntimeTable  update(@PathVariable Long id ){
        return iSbRuntimeTableService.getById(id);
    }

    @PutMapping("/{id}")
    public SbRuntimeTable  update(@PathVariable Long id , @Valid @RequestBody SbRuntimeTableReq req){
        return iSbRuntimeTableService.update(id,req);
    }

    @DeleteMapping("/{id}")
    public SimpleResp delete(@PathVariable Long id ){
        iSbRuntimeTableService.delete(id);
        return SimpleResp.SUCCESS;
    }


}
