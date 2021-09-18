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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CompanyRepository companyRepository;
    private final FaultOrderRepository faultOrderRepository;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder, CompanyRepository companyRepository, FaultOrderRepository faultOrderRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.companyRepository = companyRepository;
        this.faultOrderRepository = faultOrderRepository;
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/start";

    }

    @GetMapping("/all")
    public String userAll(Model model) {

        model.addAttribute("users", userRepository.findAll());
        return "/user/userAll.jsp";
    }

    @GetMapping("/start")
    public String start(Model model) {

        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("user", principal);
        model.addAttribute("order", faultOrderRepository.findFaultOrdersByClient((User) principal));
        return "/user/start.jsp";
    }

    @GetMapping("/add/user")
    public String userAdd(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("company", companyRepository.findAll());
        return "/user/userAddForm.jsp";

    }

    @PostMapping("/add/user")
    public String userAddPost(@Valid User user, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("company", companyRepository.findAll());
            return "/user/userAddForm.jsp";
        }
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("company", companyRepository.findAll());
            model.addAttribute("error", "Użytkownik o podanym adresie email istnieje!");
            return "/user/userAddForm.jsp";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_ADMIN");
        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/admin/users";

    }

    @GetMapping("/role")
    public String role() {

        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.getRole().equals("ROLE_ADMIN")) {
            return "redirect:/admin/start";
        }
        return "redirect:/user/start";
    }

    @GetMapping("/info")
    public String info(Model model, HttpServletRequest request) {

        User user = userRepository.findById(Long.parseLong(request.getParameter("id"))).get();
        Hibernate.initialize(user.getAddresses());
        model.addAttribute("user", user);

        return "/user/userInfo.jsp";
    }

}