package org.turkcell.ecommercepair5.service;
import java.util.List;
import org.turkcell.ecommercepair5.dto.user.*;

public interface UserService {

    void add(CreateUserDto createUserDto);

    void update(UpdateUserDto updateUserDto);

    void delete(DeleteUserDto deleteUserDto);

    List<UserListingDto> getAll();

    String login(LoginDto loginDto);

}
