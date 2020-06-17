package wiki.zex.cloud.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * 
 * </p>
 *
 * @author Zex
 * @since 2020-06-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SbStation对象", description="")
public class SbStation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long id;

    @ApiModelProperty(value = "车站名称")
    private String stationName;

    @ApiModelProperty(value = "车站编码")
    private String stationCode;
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long nextStationId;

    @ApiModelProperty(value = "下站距离")
    private Float nextStationDistance;

    @ApiModelProperty(value = "排序")
    private Integer seq;

    @ApiModelProperty(value = "描述")
    private String description;


}
