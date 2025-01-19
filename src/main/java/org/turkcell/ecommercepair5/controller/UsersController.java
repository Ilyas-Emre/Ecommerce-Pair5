package org.turkcell.ecommercepair5.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.turkcell.ecommercepair5.dto.user.*;
import org.turkcell.ecommercepair5.entity.User;
import org.turkcell.ecommercepair5.service.UserService;
import org.turkcell.ecommercepair5.util.jwt.JwtService;

import java.util.List;

@RestController
@RequestMapping("users")
public class UsersController {

    private final UserService userService;

    //, JwtService jwtService
    public UsersController(UserService userService, JwtService jwtService)
    {
        this.userService = userService;
    }

    @PostMapping("create")
    public void add(@RequestBody @Valid CreateUserDto createUserDto)

    {
        userService.add(createUserDto);
    }

    @PutMapping("update")
    public void update(@RequestBody @Valid UpdateUserDto updateUserDto)

    {
        userService.update(updateUserDto);
    }

    @PutMapping("delete")
    public void delete(@RequestBody DeleteUserDto deleteUserDto)

    {
        userService.delete(deleteUserDto);
    }

    @GetMapping
    public List<UserListingDto> getAll()

    {
        return this.userService.getAll();
    }

    @PostMapping("login")
    public String login(@RequestBody @Valid LoginDto loginDto)

    {
        return userService.login(loginDto);
    }

}

