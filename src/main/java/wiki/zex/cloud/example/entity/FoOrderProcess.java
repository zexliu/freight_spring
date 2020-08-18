package wiki.zex.cloud.example.entity;

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
import wiki.zex.cloud.example.enums.UserType;

/**
 * <p>
 * 
 * </p>
 *
 * @author Zex
 * @since 2020-07-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="FoOrderProcess对象", description="")
public class FoOrderProcess implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    private OrderType type;

    private LocalDateTime createAt;

    private String snapshot;

    private Long orderId;

    private Long userId;

    private UserType userType;

    private String description;


}
