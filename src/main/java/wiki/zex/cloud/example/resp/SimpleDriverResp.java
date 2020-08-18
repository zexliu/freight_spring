package wiki.zex.cloud.example.resp;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SimpleDriverResp {

    Long id;
    String username;
    String mobile;
    String nickname;
    String avatar;
    LocalDateTime createAt;
    String carLong;
    String carModel;
}
