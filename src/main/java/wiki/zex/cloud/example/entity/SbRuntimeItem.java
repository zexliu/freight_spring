package wiki.zex.cloud.example.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalTime;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * 时刻表数据项
 * </p>
 *
 * @author Zex
 * @since 2020-06-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SbRuntimeItem对象", description="时刻表数据项")
public class SbRuntimeItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long id;

    @ApiModelProperty(value = "所属时刻表")
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long tableId;

    @ApiModelProperty(value = "始发站ID")
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long startStationId;

    @ApiModelProperty(value = "终点站ID")
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long endStationId;

    @ApiModelProperty(value = "车次")
    private String trainNo;

    @ApiModelProperty(value = "服务号")
    private String serviceNo;

    @ApiModelProperty(value = "备注")
    private String description;

    @ApiModelProperty(value = "开点")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private LocalTime startAt;

    @ApiModelProperty(value = "到点")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private LocalTime endAt;

    @ApiModelProperty(value = "总里程")
    private Float distance;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createAt;

    @ApiModelProperty(value = "是否上行")
    private Boolean up;


}
