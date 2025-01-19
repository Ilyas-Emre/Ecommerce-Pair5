package org.turkcell.ecommercepair5.service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.turkcell.ecommercepair5.dto.user.*;
import org.turkcell.ecommercepair5.entity.User;
import org.turkcell.ecommercepair5.repository.UserRepository;
import org.turkcell.ecommercepair5.util.exception.type.BusinessException;
import org.turkcell.ecommercepair5.util.jwt.JwtService;
import java.util.List;


@Service

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public void add(CreateUserDto createUserDto) {
     User user = new User();
     user.setFirstName(createUserDto.getFirstName());
     user.setLastName(createUserDto.getLastName());
     user.setEmail(createUserDto.getEmail());
     user.setPassword(createUserDto.getPassword());
     userRepository.save(user);
    }

    @Override
    public void update(UpdateUserDto updateUserDto) {
       User userToUpdate=userRepository.findById(updateUserDto.getId()).orElseThrow(() -> new BusinessException("User not found."));
        userToUpdate.setFirstName(updateUserDto.getFirstName());
        userToUpdate.setLastName(updateUserDto.getLastName());
        userToUpdate.setEmail(updateUserDto.getEmail());
        userToUpdate.setPassword(updateUserDto.getPassword());
        userRepository.save(userToUpdate);
    }

    @Override
    public void delete(DeleteUserDto deleteUserDto) {

    }

    @Override
    public List<UserListingDto> getAll() {
        List<UserListingDto> userListingDtos = userRepository
                .findAll()
                .stream()
                .map((user) -> new UserListingDto(user.getId(),user.getFirstName(),user.getLastName(),user.getEmail()))
                .toList();
        return userListingDtos;
    }

    @Override
    public String login(LoginDto loginDto) {
        User dbUser = userRepository
                .findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new BusinessException("Invalid or wrong credentials."));


        boolean isPasswordCorrect = bCryptPasswordEncoder
                .matches(loginDto.getPassword(), dbUser.getPassword());

        if(!isPasswordCorrect)
            throw new BusinessException("Invalid or wrong credentials.");

        return this.jwtService.generateToken(dbUser.getEmail());
    }
}
