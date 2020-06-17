package wiki.zex.cloud.example.service;

import wiki.zex.cloud.example.entity.QaQuestion;
import com.baomidou.mybatisplus.extension.service.IService;
import wiki.zex.cloud.example.req.QaQuestionReq;

import java.util.List;

/**
 * <p>
 * 试题 服务类
 * </p>
 *
 * @author Zex
 * @since 2020-06-03
 */
public interface IQaQuestionService extends IService<QaQuestion> {

    QaQuestion create(QaQuestionReq req);

    QaQuestion update(Long id, QaQuestionReq req);

    List<QaQuestion> randomQuestions(Long driverId, Long questionStoreId, Integer questionCount);
}
