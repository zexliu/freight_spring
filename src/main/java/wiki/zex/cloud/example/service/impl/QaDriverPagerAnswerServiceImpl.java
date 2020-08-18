package wiki.zex.cloud.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import wiki.zex.cloud.example.entity.QaDriverPagerAnswer;
import wiki.zex.cloud.example.mapper.QaDriverPagerAnswerMapper;
import wiki.zex.cloud.example.resp.QaDriverPagerAnswerResp;
import wiki.zex.cloud.example.service.IQaDriverPagerAnswerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 司机答题表 服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-06-03
 */
@Service
public class QaDriverPagerAnswerServiceImpl extends ServiceImpl<QaDriverPagerAnswerMapper, QaDriverPagerAnswer> implements IQaDriverPagerAnswerService {

    @Override
    public List<QaDriverPagerAnswerResp> getByPagerId(Long id) {
        return baseMapper.getByPagerId(id);
    }

    @Override
    public IPage<QaDriverPagerAnswerResp> respPage(Page<QaDriverPagerAnswerResp> page) {
        return baseMapper.page(page);
    }
}
