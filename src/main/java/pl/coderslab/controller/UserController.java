package pl.coderslab.controller;

import org.hibernate.Hibernate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.entity.User;
import pl.coderslab.repository.CompanyRepository;
import pl.coderslab.repository.FaultOrderRepository;
import pl.coderslab.repository.UserRepository;
import pl.coderslab.service.UserServic;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CompanyRepository companyRepository;
    private final FaultOrderRepository faultOrderRepository;
    private final UserServic userServic;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder, CompanyRepository companyRepository, FaultOrderRepository faultOrderRepository, UserServic userServic) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.companyRepository = companyRepository;
        this.faultOrderRepository = faultOrderRepository;
        this.userServic = userServic;
    }

    @ModelAttribute
    public void addAttribute(Model model) {
        if (!SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("userName", principal.getFullName());
        }

        model.addAttribute("companys", companyRepository.findAll());

    }


    @GetMapping("/add")
    public String clientAdd(Model model) {

        model.addAttribute("user", new User());
        return "/user/clientAddForm.jsp";
    }

    @PostMapping("/add")
    public String clientAddPost(@Valid User user, BindingResult result, Model model, HttpServletRequest request) {

        if (result.hasErrors()) {
            return "/user/clientAddForm.jsp";
        }
        if(userRepository.findUserByEmail(user.getEmail()).isPresent()){
            model.addAttribute("error", "Użytkownik o podanym adresie email istnieje!");
            return "/user/clientAddForm.jsp";
        }
        userServic.registryNewAccount(user);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/start";

    }



    @GetMapping("/start")
    public String start(Model model) {

        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("order", faultOrderRepository.findFaultOrdersByClient(principal));
        return "/user/start.jsp";
    }



    @GetMapping("/role")
    public String role() {

        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.getRole().equals("ROLE_ADMIN")) {
            return "redirect:/admin/start";
        }
        return "redirect:/user/start";
    }



}