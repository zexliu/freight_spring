package wiki.zex.cloud.example.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import wiki.zex.cloud.example.enums.OrderType;
import wiki.zex.cloud.example.enums.PayType;

/**
 * <p>
 * 
 * </p>
 *
 * @author Zex
 * @since 2020-07-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="PayOrder对象", description="")
public class PayOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    private PayType orderType;

    private LocalDateTime payAt;

    private Long userId;

    private BigDecimal amount;

    private String channelType;

    private Integer status;

    private String subject;

    private String body;

    private String ipAddress;

    private LocalDateTime createAt;

    private LocalDateTime expireAt;

    private String thirdPartyId;

    private Long foOrderId;

    private Long foDeliveryId;


}
