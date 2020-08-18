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
public class QaDriverPagerAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long id;

    @ApiModelProperty(value = "问题ID")
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long questionId;

    @ApiModelProperty(value = "试卷ID")
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long questionPagerId;

    @ApiModelProperty(value = "答案")
    private String answer;

    @ApiModelProperty(value = "司机ID")
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long driverId;

    @ApiModelProperty(value = "问题分数")
    private Integer questionScore;

    @ApiModelProperty(value = "是否正确")
    private Boolean right;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createAt;


}
