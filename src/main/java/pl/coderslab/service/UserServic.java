package pl.coderslab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.coderslab.controller.FaultOrderController;
import pl.coderslab.entity.FaultOrder;
import pl.coderslab.entity.User;
import pl.coderslab.repository.FaultOrderRepository;
import pl.coderslab.repository.UserRepository;

import javax.validation.constraints.Email;
import java.util.List;


@Configuration
public class UserServic {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final FaultOrderRepository faultOrderRepository;

    public UserServic(PasswordEncoder passwordEncoder, UserRepository userRepository, FaultOrderRepository faultOrderRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.faultOrderRepository = faultOrderRepository;
    }

    public User registryNewAccount(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public User registtyNewWorker(User user){

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_WORKER");
        return userRepository.save(user);
    }

    public Boolean emailExist(String email){
        return userRepository.existsUserByEmail(email);
    }

    public List<FaultOrder> findFaultOrderByClient(User client){
        return  faultOrderRepository.findFaultOrdersByClient(client);
    }

}
