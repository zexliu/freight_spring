package wiki.zex.cloud.example.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import wiki.zex.cloud.example.entity.SbRuntimeItem;
import wiki.zex.cloud.example.req.Pageable;
import wiki.zex.cloud.example.req.SbRuntimeItemReq;
import wiki.zex.cloud.example.resp.SbRuntimeItemResp;
import wiki.zex.cloud.example.resp.SimpleResp;
import wiki.zex.cloud.example.service.ISbRuntimeItemService;

import javax.validation.Valid;

/**
 * <p>
 * 时刻表数据项 前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-06-15
 */
@RestController
@RequestMapping("/api/v1/runtime/items")
@Api(tags = "时刻表数据项")
public class SbRuntimeItemController {

    @Autowired
    private ISbRuntimeItemService iSbRuntimeItemService;

    @GetMapping
    public IPage<SbRuntimeItemResp> list(Pageable pageable, Long startStationId, Long endStationId,Long  tableId,String serviceNo,String trainNo,Boolean up){
        return iSbRuntimeItemService.page(pageable.convert(),startStationId,endStationId,tableId,serviceNo,trainNo,up);
    }

    @GetMapping("/{id}")
    public SbRuntimeItem  update(@PathVariable Long id ){
        return iSbRuntimeItemService.getById(id);
    }
    @PostMapping
    public SbRuntimeItem  create(@Valid @RequestBody SbRuntimeItemReq req){
        return iSbRuntimeItemService.create(req);
    }

    @PutMapping("/{id}")
    public SbRuntimeItem  update(@PathVariable Long id , @Valid @RequestBody SbRuntimeItemReq req){
        return iSbRuntimeItemService.update(id,req);
    }

    @DeleteMapping("/{id}")
    public SimpleResp delete(@PathVariable Long id ){
        iSbRuntimeItemService.removeById(id);
        return SimpleResp.SUCCESS;
    }

}
