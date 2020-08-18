package wiki.zex.cloud.example.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import wiki.zex.cloud.example.entity.SbRuntimeTable;
import wiki.zex.cloud.example.mapper.SbRuntimeTableMapper;
import wiki.zex.cloud.example.req.SbRuntimeTableReq;
import wiki.zex.cloud.example.service.ISbRuntimeItemService;
import wiki.zex.cloud.example.service.ISbRuntimeTableService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 时刻表 服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-06-15
 */
@Service
public class SbRuntimeTableServiceImpl extends ServiceImpl<SbRuntimeTableMapper, SbRuntimeTable> implements ISbRuntimeTableService {


    @Autowired
    private ISbRuntimeItemService iSbRuntimeItemService;
    @Override
    public SbRuntimeTable create(SbRuntimeTableReq req) {
        SbRuntimeTable table = new SbRuntimeTable();
        BeanUtils.copyProperties(req,table);
        save(table);
        return table;
    }

    @Override
    public SbRuntimeTable update(Long id, SbRuntimeTableReq req) {
        SbRuntimeTable table = new SbRuntimeTable();
        BeanUtils.copyProperties(req,table);
        table.setId(id);
        updateById(table);
        return table;
    }

    @Override
    public void delete(Long id) {
        iSbRuntimeItemService.removeByTableId(id);
        removeById(id);
    }
}
