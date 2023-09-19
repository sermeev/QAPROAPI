
package dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UserDTO {
    private String email;
    private String firstName;
    private Long id;
    private String lastName;
    private String password;
    private String phone;
    private Long userStatus;
    private String username;
}
