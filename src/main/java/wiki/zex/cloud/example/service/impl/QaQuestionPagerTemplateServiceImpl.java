package wiki.zex.cloud.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import wiki.zex.cloud.example.entity.QaQuestionPagerTemplate;
import wiki.zex.cloud.example.exception.ServerException;
import wiki.zex.cloud.example.mapper.QaQuestionPagerTemplateMapper;
import wiki.zex.cloud.example.req.QaQuestionPagerTemplateReq;
import wiki.zex.cloud.example.service.IQaQuestionPagerTemplateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 试卷模板 服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-06-03
 */
@Service
public class QaQuestionPagerTemplateServiceImpl extends ServiceImpl<QaQuestionPagerTemplateMapper, QaQuestionPagerTemplate> implements IQaQuestionPagerTemplateService {

    @Override
    public QaQuestionPagerTemplate create(QaQuestionPagerTemplateReq req) {
        QaQuestionPagerTemplate template = new QaQuestionPagerTemplate();
        BeanUtils.copyProperties(req,template);
        save(template);
        return template;
    }

    @Override
    public QaQuestionPagerTemplate update(Long id, QaQuestionPagerTemplateReq req) {
        QaQuestionPagerTemplate template = new QaQuestionPagerTemplate();
        template.setId(id);
        BeanUtils.copyProperties(req,template);
        updateById(template);
        return template;
    }

    @Override
    public void delete(Long id) {
        removeById(id);
    }

    @Override
    @Transactional
    public void enable(Long id) {
        QaQuestionPagerTemplate template = new QaQuestionPagerTemplate();
        template.setEnable(false);
        update(template,new LambdaUpdateWrapper<QaQuestionPagerTemplate>().ne(QaQuestionPagerTemplate::getId,id));
        template.setEnable(true);
        update(template,new LambdaUpdateWrapper<QaQuestionPagerTemplate>().eq(QaQuestionPagerTemplate::getId,id));

    }

    @Override
    public QaQuestionPagerTemplate getCurrentTemplate() {
        QaQuestionPagerTemplate template =  getOne(new LambdaQueryWrapper<QaQuestionPagerTemplate>().eq(QaQuestionPagerTemplate::getEnable,true));
        if (template == null){
            throw new ServerException("可用试卷模板不存在");
        }
        return template;
    }
}
