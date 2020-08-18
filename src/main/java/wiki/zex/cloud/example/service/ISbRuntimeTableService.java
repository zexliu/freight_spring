package wiki.zex.cloud.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import wiki.zex.cloud.example.entity.SbRuntimeTable;
import com.baomidou.mybatisplus.extension.service.IService;
import wiki.zex.cloud.example.req.SbRuntimeTableReq;

/**
 * <p>
 * 时刻表 服务类
 * </p>
 *
 * @author Zex
 * @since 2020-06-15
 */
public interface ISbRuntimeTableService extends IService<SbRuntimeTable> {

    SbRuntimeTable create(SbRuntimeTableReq req);

    SbRuntimeTable update(Long id, SbRuntimeTableReq req);

    void delete(Long id);
}
