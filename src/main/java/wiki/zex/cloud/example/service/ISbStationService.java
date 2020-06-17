package wiki.zex.cloud.example.service;

import wiki.zex.cloud.example.entity.SbStation;
import com.baomidou.mybatisplus.extension.service.IService;
import wiki.zex.cloud.example.req.SbStationReq;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-06-12
 */
public interface ISbStationService extends IService<SbStation> {

    SbStation create(SbStationReq req);

    SbStation update(Long id, SbStationReq req);

    List<SbStation> downLine();


    List<SbStation> upLine();

}
