package wiki.zex.cloud.example.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import wiki.zex.cloud.example.entity.SbRuntimeItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import wiki.zex.cloud.example.resp.SbRuntimeItemResp;

/**
 * <p>
 * 时刻表数据项 Mapper 接口
 * </p>
 *
 * @author Zex
 * @since 2020-06-15
 */
public interface SbRuntimeItemMapper extends BaseMapper<SbRuntimeItem> {

    IPage<SbRuntimeItemResp> page(Page<SbRuntimeItemResp> page, @Param("startStationId") Long startStationId,
                                  @Param("endStationId") Long endStationId,
                                  @Param("tableId") Long tableId,
                                  @Param("serviceNo") String serviceNo,
                                  @Param("trainNo")   String trainNo,
                                  @Param("up")  Boolean up);

}
