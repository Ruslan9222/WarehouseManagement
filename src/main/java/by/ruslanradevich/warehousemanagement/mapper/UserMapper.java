package by.ruslanradevich.warehousemanagement.mapper;

import by.ruslanradevich.warehousemanagement.dto.CreateUserDto;
import by.ruslanradevich.warehousemanagement.dto.LoginUserDto;
import by.ruslanradevich.warehousemanagement.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User createUserToEntity(CreateUserDto createUserDto) {
        User user = new User();
        user.setUsername(createUserDto.getUsername());
        user.setPassword(createUserDto.getPassword());
        return user;
    }


    public User loginUserToEntity(LoginUserDto loginUserDto) {
        User user = new User();
        user.setUsername(loginUserDto.getUsername());
        user.setPassword(loginUserDto.getPassword());
        return user;
    }

}
