package by.ruslanradevich.warehousemanagement.controller;

import by.ruslanradevich.warehousemanagement.dto.CreateUserDto;
import by.ruslanradevich.warehousemanagement.dto.LoginUserDto;
import by.ruslanradevich.warehousemanagement.entity.User;
import by.ruslanradevich.warehousemanagement.mapper.UserMapper;
import by.ruslanradevich.warehousemanagement.repository.UserRepository;
import by.ruslanradevich.warehousemanagement.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserRepository userRepository, UserService userService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public String getHomePage() {
        return "user/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "user/home";
    }

    @PostMapping("/new")
    public String showFormForNewUser(Model model) {
        CreateUserDto createUserDto = new CreateUserDto();
        model.addAttribute("user", createUserDto);
        return "user/new";
    }

    @GetMapping("/login")
    public String showFormForLogin(Model model) {
        model.addAttribute("user", new LoginUserDto());
        return "user/login";
    }

    @PostMapping("/new")
    public String create(@Valid @ModelAttribute("user") CreateUserDto createUserDto,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/new";
        }
        userService.save(userMapper.createUserToEntity(createUserDto));
        return "user/home";
    }
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("user") LoginUserDto loginUserDto,
                        BindingResult bindingResult,
                        HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "user/login";
        }
        User user = userMapper.loginUserToEntity(loginUserDto);
        session.setAttribute("currentUser", userService.getUserById(user.getId()));
        System.out.println(userService.getUserById(user.getId()));
        return "user/home";
    }
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, HttpSession session) {
        userService.deleteUser();
        session.invalidate();
        return "user/home";
    }
}
