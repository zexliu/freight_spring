package wiki.zex.cloud.example.service.impl;

import org.springframework.beans.BeanUtils;
import wiki.zex.cloud.example.entity.QaQuestion;
import wiki.zex.cloud.example.entity.QaQuestionStore;
import wiki.zex.cloud.example.exception.ServerException;
import wiki.zex.cloud.example.mapper.QaQuestionMapper;
import wiki.zex.cloud.example.req.QaQuestionReq;
import wiki.zex.cloud.example.service.IQaQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 试题 服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-06-03
 */
@Service
public class QaQuestionServiceImpl extends ServiceImpl<QaQuestionMapper, QaQuestion> implements IQaQuestionService {

    @Override
    public QaQuestion create(QaQuestionReq req) {
        QaQuestion qaQuestion = new QaQuestion();
        BeanUtils.copyProperties(req,qaQuestion);
        save(qaQuestion);
        return qaQuestion;
    }

    @Override
    public QaQuestion update(Long id, QaQuestionReq req) {
        QaQuestion qaQuestion = new QaQuestion();
        qaQuestion.setId(id);
        BeanUtils.copyProperties(req,qaQuestion);
        updateById(qaQuestion);
        return qaQuestion;
    }

    @Override
    public List<QaQuestion> randomQuestions(Long driverId, Long questionStoreId, Integer questionCount) {
        List<QaQuestion> questions =  baseMapper.randomQuestions(driverId,questionStoreId,questionCount);
        if (questions.size()  <  questionCount){
            throw new ServerException("题库题目数量低于模板数量");
        }
        return questions;
    }
}
