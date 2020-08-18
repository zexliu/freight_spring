package wiki.zex.cloud.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import wiki.zex.cloud.example.entity.SbRuntimeItem;
import com.baomidou.mybatisplus.extension.service.IService;
import wiki.zex.cloud.example.req.SbRuntimeItemReq;
import wiki.zex.cloud.example.resp.SbRuntimeItemResp;

/**
 * <p>
 * 时刻表数据项 服务类
 * </p>
 *
 * @author Zex
 * @since 2020-06-15
 */
public interface ISbRuntimeItemService extends IService<SbRuntimeItem> {

    void removeByTableId(Long id);

    SbRuntimeItem create(SbRuntimeItemReq req);

    SbRuntimeItem update(Long id, SbRuntimeItemReq req);

    IPage<SbRuntimeItemResp> page(Page<SbRuntimeItemResp> convert, Long startStationId, Long endStationId, Long tableId, String serviceNo, String trainNo, Boolean up);
}
