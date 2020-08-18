package wiki.zex.cloud.example.service;

import wiki.zex.cloud.example.entity.QaQuestionStore;
import com.baomidou.mybatisplus.extension.service.IService;
import wiki.zex.cloud.example.req.QaQuestionStoreReq;

/**
 * <p>
 * 试题题库 服务类
 * </p>
 *
 * @author Zex
 * @since 2020-06-03
 */
public interface IQaQuestionStoreService extends IService<QaQuestionStore> {

    QaQuestionStore create(QaQuestionStoreReq req);

    QaQuestionStore update(Long id, QaQuestionStoreReq req);

}
