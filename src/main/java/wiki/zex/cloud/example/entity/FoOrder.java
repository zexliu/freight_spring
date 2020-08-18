package wiki.zex.cloud.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Zex
 * @since 2020-07-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="FoOrder对象", description="")
public class FoOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    private Long deliveryId;

    private Long userId;

    private Long driverId;

    private BigDecimal amount;

    private BigDecimal freightAmount;

    private Boolean confirmStatus;

    private Boolean transportStatus;

    private Boolean payStatus;

    private Boolean evaluateStatus;

    private Boolean driverEvaluateStatus;

    private Boolean driverPayStatus;

    private Boolean cancelStatus;

    private Boolean refundStatus;

    private Integer protocolStatus;


}
