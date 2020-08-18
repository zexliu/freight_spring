package wiki.zex.cloud.example.resp;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
public class OrderResp {

    private Long orderId;

    private Long userId;

    private Long driverId;

    private Long deliveryId;

   private String driverNickname;

   private String driverAvatar;

   private String userNickname;

   private String userAvatar;

   private String driverMobile;

   private String avatarMobile;

    private String loadProvinceCode;

    private String loadCityCode;

    private String loadDistrictCode;

    private String unloadProvinceCode;

    private String unloadCityCode;

    private String unloadDistrictCode;

    private LocalDateTime loadStartAt;

    private LocalDateTime loadEndAt;

    private BigDecimal amount;

    private Boolean confirmStatus;

    private Boolean transportStatus;

    private Boolean payStatus;

    private Boolean evaluateStatus;

    private Boolean driverEvaluateStatus;

    private Boolean cancelStatus;

    private Boolean refundStatus;

    private Integer protocolStatus;
    private Boolean driverPayStatus;

}
