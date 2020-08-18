package wiki.zex.cloud.example.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import wiki.zex.cloud.example.entity.QaDriverPagerAnswer;
import wiki.zex.cloud.example.entity.QaDriverQuestionPager;
import wiki.zex.cloud.example.entity.QaQuestion;
import wiki.zex.cloud.example.entity.QaQuestionPagerTemplate;
import wiki.zex.cloud.example.enums.PagerStatus;
import wiki.zex.cloud.example.exception.ForbiddenException;
import wiki.zex.cloud.example.mapper.QaDriverQuestionPagerMapper;
import wiki.zex.cloud.example.resp.QaDriverPagerAnswerResp;
import wiki.zex.cloud.example.resp.QaDriverQuestionPagerResp;
import wiki.zex.cloud.example.security.MyUserDetails;
import wiki.zex.cloud.example.service.IQaDriverPagerAnswerService;
import wiki.zex.cloud.example.service.IQaDriverQuestionPagerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import wiki.zex.cloud.example.service.IQaQuestionPagerTemplateService;
import wiki.zex.cloud.example.service.IQaQuestionService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户答题试卷 服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-06-03
 */
@Service
public class QaDriverQuestionPagerServiceImpl extends ServiceImpl<QaDriverQuestionPagerMapper, QaDriverQuestionPager> implements IQaDriverQuestionPagerService {

    @Autowired
    private IQaQuestionPagerTemplateService iQaQuestionPagerTemplateService;

    @Autowired
    private IQaQuestionService iQaQuestionService;

    @Autowired
    private IQaDriverPagerAnswerService iQaDriverPagerAnswerService;

    @Transactional
    @Override
    public QaDriverQuestionPagerResp generatePager(Authentication authentication) {
        QaQuestionPagerTemplate template = iQaQuestionPagerTemplateService.getCurrentTemplate();
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        QaDriverQuestionPager pager = new QaDriverQuestionPager();
        pager.setQuestionCount(template.getQuestionCount());
        pager.setQuestionStoreId(template.getQuestionStoreId());
        pager.setTemplateId(template.getId());
        pager.setStatus(PagerStatus.GENERATED);
        pager.setDriverId(userDetails.getId());

        //随机获取问题
        List<QaQuestion> questions = iQaQuestionService.randomQuestions(userDetails.getId(), pager.getQuestionStoreId(), pager.getQuestionCount());
        //问题集总分数
        int totalScore = questions.stream().mapToInt(QaQuestion::getQuestionScore).sum();
        pager.setTotalScore(totalScore);

        //存储试卷信息
        save(pager);

        List<QaDriverPagerAnswer> answers = questions.stream().map(qaQuestion -> {
            QaDriverPagerAnswer answer = new QaDriverPagerAnswer();
            answer.setDriverId(userDetails.getId());
            answer.setQuestionId(qaQuestion.getId());
            answer.setQuestionPagerId(pager.getId());
            answer.setQuestionScore(qaQuestion.getQuestionScore());
            return answer;
        }).collect(Collectors.toList());
        //存储答案信息
        iQaDriverPagerAnswerService.saveBatch(answers);
        QaDriverQuestionPagerResp resp = new QaDriverQuestionPagerResp();
        BeanUtils.copyProperties(pager,resp);
        resp.setQuestions(questions);
        return resp;
    }

    @Override
    @Transactional
    public void submit(Long id, Authentication authentication, Map<Long, String> map) {
        QaDriverQuestionPager pager = getById(id);
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        if (!pager.getDriverId().equals(myUserDetails.getId())){
            throw new ForbiddenException("无权限提交该条记录");
        }

        List<QaDriverPagerAnswerResp> answers = iQaDriverPagerAnswerService.getByPagerId(id);
        int totalScore = 0;
        for (QaDriverPagerAnswerResp answer : answers) {
            String questionAnswer = map.get(answer.getId());
            if (StringUtils.equals(questionAnswer,answer.getQuestionAnswer())){
                answer.setRight(true);
                totalScore+= answer.getQuestionScore();
            }else {
                answer.setRight(false);
            }
        }
        //转换answer实体
        List<QaDriverPagerAnswer> collect = answers.stream().map(qaDriverPagerAnswerResp -> {
            QaDriverPagerAnswer answer = new QaDriverPagerAnswer();
            answer.setRight(qaDriverPagerAnswerResp.getRight());
            answer.setId(qaDriverPagerAnswerResp.getId());
            return answer;
        }).collect(Collectors.toList());

        //存储答案信息

        iQaDriverPagerAnswerService.saveBatch(collect);


        pager.setDriverScore(totalScore);
        pager.setEffect(true);
        pager.setStatus(PagerStatus.SUBMITTED);
        pager.setSubmitAt(LocalDateTime.now());
        pager.setTimeDuration((int) ((pager.getSubmitAt().toEpochSecond(ZoneOffset.UTC) - pager.getCreateAt().toEpochSecond(ZoneOffset.UTC))/ 1000));
        //存储试卷信息
        save(pager);
    }
}
