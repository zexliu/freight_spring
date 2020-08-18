package wiki.zex.cloud.example.resp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import wiki.zex.cloud.example.config.serializers.JsonLongSerializer;
import wiki.zex.cloud.example.entity.SbRuntimeItem;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * <p>
 * 时刻表数据项
 * </p>
 *
 * @author Zex
 * @since 2020-06-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="SbRuntimeItem对象", description="时刻表数据项")
public class SbRuntimeItemResp extends SbRuntimeItem {

    @ApiModelProperty(value = "始发站ID")
    private String startStationName;

    @ApiModelProperty(value = "终点站ID")
    private String endStationName;


}
