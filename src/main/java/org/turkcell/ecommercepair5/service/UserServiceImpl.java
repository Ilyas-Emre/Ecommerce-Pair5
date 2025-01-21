package org.turkcell.ecommercepair5.service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.turkcell.ecommercepair5.dto.user.*;
import org.turkcell.ecommercepair5.entity.Cart;
import org.turkcell.ecommercepair5.entity.User;
import org.turkcell.ecommercepair5.repository.UserRepository;
import org.turkcell.ecommercepair5.util.exception.type.BusinessException;
import org.turkcell.ecommercepair5.util.jwt.JwtService;
import java.util.List;
import java.util.Optional;


@Service

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;
    private final OrderService orderService;
    private final CartService cartService;

    public UserServiceImpl(UserRepository userRepository, JwtService jwtService, CartService cartService, OrderService orderService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.cartService = cartService;
        this.orderService = orderService;
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public void add(CreateUserDto createUserDto) {
     User user = new User();
     user.setFirstName(createUserDto.getFirstName());
     user.setLastName(createUserDto.getLastName());
     user.setEmail(createUserDto.getEmail());
     user.setPassword(bCryptPasswordEncoder.encode(createUserDto.getPassword()));
     user.setIsActive(true);
     userRepository.save(user);

        // Save the cart
        cartService.CreateCart(user);
    }

    @Override
    public void update(UpdateUserDto updateUserDto) {
       User userToUpdate = userRepository.findById(updateUserDto.getId())
               .orElseThrow(() -> new BusinessException("User not found."));
        userToUpdate.setFirstName(updateUserDto.getFirstName());
        userToUpdate.setLastName(updateUserDto.getLastName());
        userToUpdate.setEmail(updateUserDto.getEmail());
        userToUpdate.setPassword(bCryptPasswordEncoder.encode(updateUserDto.getPassword()));
        userRepository.save(userToUpdate);
    }

    @Override
    public void delete(DeleteUserDto deleteUserDto) {
        List<Integer> idsToDelete = deleteUserDto.getId();

        for (Integer id : idsToDelete) {
            User userToDelete = userRepository.findById(id)
                    .orElseThrow(() -> new BusinessException("User not found with id: " + id));
            // Deactivate the user
            userToDelete.setIsActive(false);
            userRepository.save(userToDelete);

//            // Find and deactivate orders for this user
//            List<Order> userOrders = orderService.findByUserId(id);
//            userOrders.forEach(order -> order.setIsActive(false));
//            orderService.saveAll(userOrders);

            orderService.deleteOrdersForAUser(id);

//            // Find and deactivate the cart for this user (since only one cart exists)
//            Cart userCart = cartService.findByUserId(id)
//                    .orElseThrow(() -> new BusinessException("Cart not found for user with id: " + id)); // Unwrap Optional
//            userCart.setIsActive(false);
//            cartService.save(userCart);  // Save the deactivated cart

            cartService.deleteCartForAUser(id);

        }
    }

    @Override
    public List<UserListingDto> getAll() {
        List<UserListingDto> userListingDtos = userRepository
                .findAll()
                .stream()
                .filter(user -> user.getIsActive())
                .map((user) -> new UserListingDto(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getIsActive()))
                .toList();
        return userListingDtos;
    }

    @Override
    public String login(LoginDto loginDto) {
        User dbUser = userRepository
                .findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new BusinessException("Invalid or wrong credentials."));

        // Check if the user is active
        if (!dbUser.getIsActive()) {
            throw new BusinessException("Your account is inactive. Please contact support.");
        }

        boolean isPasswordCorrect = bCryptPasswordEncoder
                .matches(loginDto.getPassword(), dbUser.getPassword());

        if(!isPasswordCorrect)
            throw new BusinessException("Invalid or wrong credentials.");

        return this.jwtService.generateToken(dbUser.getEmail());
    }
}
