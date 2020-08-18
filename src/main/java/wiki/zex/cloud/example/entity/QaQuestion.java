package wiki.zex.cloud.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import wiki.zex.cloud.example.config.serializers.JsonLongSerializer;
import wiki.zex.cloud.example.enums.QuestionType;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 试题
 * </p>
 *
 * @author Zex
 * @since 2020-06-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="QaQuestion对象", description="试题")
public class QaQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long id;

    @ApiModelProperty(value = "题库编号")
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long questionStoreId;

    @ApiModelProperty(value = "问题类型")
    private QuestionType questionType;

    @ApiModelProperty(value = "问题内容")
    private String questionContent;

    @ApiModelProperty(value = "答案选项")
    private String questionAnswerItems;

    @ApiModelProperty(value = "正确答案")
    private String questionAnswer;

    @ApiModelProperty(value = "分值")
    private Integer questionScore;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createAt;

    @ApiModelProperty(value = "删除标记")
    private Boolean delFlag;


}
