package wiki.zex.cloud.example.resp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import wiki.zex.cloud.example.config.serializers.JsonLongSerializer;

@Data
public class DeliveryGoodsMessage {

    private Integer type = 1;

    private Long deliveryId;

    private Long onlineId;


}
