package wiki.zex.cloud.example.service.impl;

import org.springframework.beans.BeanUtils;
import wiki.zex.cloud.example.entity.QaQuestionStore;
import wiki.zex.cloud.example.mapper.QaQuestionStoreMapper;
import wiki.zex.cloud.example.req.QaQuestionStoreReq;
import wiki.zex.cloud.example.service.IQaQuestionStoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 试题题库 服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-06-03
 */
@Service
public class QaQuestionStoreServiceImpl extends ServiceImpl<QaQuestionStoreMapper, QaQuestionStore> implements IQaQuestionStoreService {

    @Override
    public QaQuestionStore create(QaQuestionStoreReq req) {
        QaQuestionStore qaQuestionStore = new QaQuestionStore();
        BeanUtils.copyProperties(req,qaQuestionStore);
        save(qaQuestionStore);
        return qaQuestionStore;
    }

    @Override
    public QaQuestionStore update(Long id, QaQuestionStoreReq req) {
        QaQuestionStore qaQuestionStore = new QaQuestionStore();
        qaQuestionStore.setId(id);
        BeanUtils.copyProperties(req,qaQuestionStore);
        updateById(qaQuestionStore);
        return qaQuestionStore;
    }
}
