package wiki.zex.cloud.example.req;

import lombok.Data;
import wiki.zex.cloud.example.enums.GenderType;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class AuthenticateOwnerReq {


    @NotBlank
    private String avatar;

    private String national;
    @NotBlank
    private String identityCardNo;
    @NotBlank
    private String identityCard;

    private LocalDate birthDay;

    private GenderType genderType;

    private String address;

    private String realName;

    private String nature;
}
