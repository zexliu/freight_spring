package wiki.zex.cloud.example.service;

import wiki.zex.cloud.example.entity.QaQuestionPagerTemplate;
import com.baomidou.mybatisplus.extension.service.IService;
import wiki.zex.cloud.example.req.QaQuestionPagerTemplateReq;

/**
 * <p>
 * 试卷模板 服务类
 * </p>
 *
 * @author Zex
 * @since 2020-06-03
 */
public interface IQaQuestionPagerTemplateService extends IService<QaQuestionPagerTemplate> {

    QaQuestionPagerTemplate create(QaQuestionPagerTemplateReq req);

    QaQuestionPagerTemplate update(Long id, QaQuestionPagerTemplateReq req);

    void delete(Long id);

    void enable(Long id);

    QaQuestionPagerTemplate getCurrentTemplate();

}
