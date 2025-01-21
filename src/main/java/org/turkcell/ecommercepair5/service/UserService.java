package org.turkcell.ecommercepair5.service;
import java.util.List;
import java.util.Optional;

import org.turkcell.ecommercepair5.dto.cart.CreateCartDto;
import org.turkcell.ecommercepair5.dto.user.*;
import org.turkcell.ecommercepair5.entity.Category;
import org.turkcell.ecommercepair5.entity.User;

public interface UserService {

    Optional<User> findById(Integer id);

    void add(CreateUserDto createUserDto);

    void update(UpdateUserDto updateUserDto);

    void delete(DeleteUserDto deleteUserDto);

    List<UserListingDto> getAll();

    String login(LoginDto loginDto);

}
