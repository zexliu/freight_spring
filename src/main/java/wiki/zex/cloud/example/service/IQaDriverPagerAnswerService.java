package wiki.zex.cloud.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import wiki.zex.cloud.example.entity.QaDriverPagerAnswer;
import com.baomidou.mybatisplus.extension.service.IService;
import wiki.zex.cloud.example.req.Pageable;
import wiki.zex.cloud.example.resp.QaDriverPagerAnswerResp;

import java.util.List;

/**
 * <p>
 * 司机答题表 服务类
 * </p>
 *
 * @author Zex
 * @since 2020-06-03
 */
public interface IQaDriverPagerAnswerService extends IService<QaDriverPagerAnswer> {

    List<QaDriverPagerAnswerResp> getByPagerId(Long id);

    IPage<QaDriverPagerAnswerResp> respPage(Page<QaDriverPagerAnswerResp> page);

}
