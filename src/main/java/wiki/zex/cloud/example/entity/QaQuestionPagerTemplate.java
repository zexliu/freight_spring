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

/**
 * <p>
 * 试卷模板
 * </p>
 *
 * @author Zex
 * @since 2020-06-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="QaQuestionPagerTemplate对象", description="试卷模板")
public class QaQuestionPagerTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long id;

    @ApiModelProperty(value = "题库编号")
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long questionStoreId;
    @ApiModelProperty(value = "模板名称")
    private String pagerTemplateName;
    @ApiModelProperty(value = "题目数量")
    private Integer questionCount;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createAt;

    @ApiModelProperty(value = "删除状态")
    private Boolean delFlag;

    @ApiModelProperty(value = "启用状态")
    private Boolean enable;
}
