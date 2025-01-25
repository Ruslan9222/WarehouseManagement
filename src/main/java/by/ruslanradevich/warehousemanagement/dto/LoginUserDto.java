package by.ruslanradevich.warehousemanagement.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginUserDto {
    @NotNull
    @NotEmpty(message = "Should be not empty")
    private String username;

    @NotNull
    @NotEmpty(message = "Should be not empty")
    private String password;
}
