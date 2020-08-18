package wiki.zex.cloud.example.resp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import wiki.zex.cloud.example.config.serializers.JsonLongSerializer;
import wiki.zex.cloud.example.entity.QaDriverPagerAnswer;
import wiki.zex.cloud.example.enums.QuestionType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 司机答题表
 * </p>
 *
 * @author Zex
 * @since 2020-06-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="QaUserPagerAnswer对象", description="司机答题表")
public class QaDriverPagerAnswerResp extends QaDriverPagerAnswer {

    @ApiModelProperty(value = "正确答案")
    private String questionAnswer;

    @ApiModelProperty(value = "问题类型")
    private QuestionType questionType;

    @ApiModelProperty(value = "答案选项")
    private String questionAnswerItems;

    @ApiModelProperty(value = "问题内容")
    private String questionContent;
}
