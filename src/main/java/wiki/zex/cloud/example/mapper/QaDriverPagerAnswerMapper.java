package wiki.zex.cloud.example.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import wiki.zex.cloud.example.entity.QaDriverPagerAnswer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import wiki.zex.cloud.example.resp.QaDriverPagerAnswerResp;

import java.util.List;

/**
 * <p>
 * 司机答题表 Mapper 接口
 * </p>
 *
 * @author Zex
 * @since 2020-06-03
 */
public interface QaDriverPagerAnswerMapper extends BaseMapper<QaDriverPagerAnswer> {

    List<QaDriverPagerAnswerResp> getByPagerId(@Param("pagerId") Long id);

    IPage<QaDriverPagerAnswerResp> page(Page<QaDriverPagerAnswerResp> page);

}
