package wiki.zex.cloud.example.service;

import org.springframework.security.core.Authentication;
import wiki.zex.cloud.example.entity.QaDriverQuestionPager;
import com.baomidou.mybatisplus.extension.service.IService;
import wiki.zex.cloud.example.resp.QaDriverQuestionPagerResp;

import java.util.Map;

/**
 * <p>
 * 用户答题试卷 服务类
 * </p>
 *
 * @author Zex
 * @since 2020-06-03
 */
public interface IQaDriverQuestionPagerService extends IService<QaDriverQuestionPager> {

    QaDriverQuestionPagerResp generatePager(Authentication authentication);

    void submit(Long id, Authentication authentication, Map<Long, String> map);

}
