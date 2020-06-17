package wiki.zex.cloud.example.mapper;

import org.apache.ibatis.annotations.Param;
import wiki.zex.cloud.example.entity.QaQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 试题 Mapper 接口
 * </p>
 *
 * @author Zex
 * @since 2020-06-03
 */
public interface QaQuestionMapper extends BaseMapper<QaQuestion> {

    List<QaQuestion> randomQuestions(@Param("driverId") Long driverId, @Param("questionStoreId") Long questionStoreId, @Param("questionCount") Integer questionCount);
}
