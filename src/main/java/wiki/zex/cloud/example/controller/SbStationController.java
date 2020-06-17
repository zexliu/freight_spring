package wiki.zex.cloud.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import wiki.zex.cloud.example.entity.SbStation;
import wiki.zex.cloud.example.req.Pageable;
import wiki.zex.cloud.example.req.SbStationReq;
import wiki.zex.cloud.example.resp.SimpleResp;
import wiki.zex.cloud.example.service.ISbStationService;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-06-12
 */
@RestController
@Api(tags = "车站信息")
@RequestMapping("/api/v1/stations")
public class SbStationController {

    @Autowired
    private ISbStationService iSbStationService;


    @GetMapping
    public IPage<SbStation> list(Pageable pageable){
        return iSbStationService.page(pageable.convert()
                ,new LambdaQueryWrapper<SbStation>().orderByDesc(SbStation::getSeq).orderByDesc(SbStation::getId));
    }

    @PostMapping
    public SbStation create(@RequestBody @Valid  SbStationReq req){
        return iSbStationService.create(req);
    }

    @GetMapping("/{id}")
    public SbStation getById(@PathVariable Long id){
        return iSbStationService.getById(id);
    }

    @PutMapping("/{id}")
    public SbStation update(@PathVariable Long id , @RequestBody @Valid  SbStationReq req){
        return iSbStationService.update(id,req);
    }
    @DeleteMapping("/{id}")
    public SimpleResp delete(@PathVariable Long id){
        iSbStationService.removeById(id);
        return SimpleResp.SUCCESS;
    }

}
