package ce.mnu.site.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserDto {

    private String password;
    private String newPassword;
    private String checkPassword;
    private String tel;
    private String address;
}
