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
import wiki.zex.cloud.example.config.serializers.JsonLongSerializer;
import wiki.zex.cloud.example.enums.PagerStatus;

/**
 * <p>
 * 用户答题试卷
 * </p>
 *
 * @author Zex
 * @since 2020-06-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="QaDriverQuestionPager对象", description="用户答题试卷")
public class QaDriverQuestionPager implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long id;

    @ApiModelProperty(value = "模板ID")
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long templateId;

    @ApiModelProperty(value = "司机ID")
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long driverId;

    @ApiModelProperty(value = "题库ID")
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long questionStoreId;

    @ApiModelProperty(value = "总分数")
    private Integer totalScore;

    @ApiModelProperty(value = "答题数量")
    private Integer questionCount;

    @ApiModelProperty(value = "总用时")
    private Integer timeDuration;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createAt;

    @ApiModelProperty(value = "提交时间")
    private LocalDateTime submitAt;

    @ApiModelProperty(value = "司机得分")
    private Integer driverScore;

    @ApiModelProperty(value = "状态")
    private PagerStatus status;

    @ApiModelProperty(value = "是否有效")
    private Boolean effect;
}
