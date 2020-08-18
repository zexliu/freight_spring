package wiki.zex.cloud.example.resp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import wiki.zex.cloud.example.entity.QaDriverQuestionPager;
import wiki.zex.cloud.example.entity.QaQuestion;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper = true)
public class QaDriverQuestionPagerResp extends QaDriverQuestionPager {
    List<QaQuestion> questions;
}
