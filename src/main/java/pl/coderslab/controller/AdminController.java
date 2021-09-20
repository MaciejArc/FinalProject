package pl.coderslab.controller;

import org.hibernate.Hibernate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.entity.User;
import pl.coderslab.repository.CompanyRepository;
import pl.coderslab.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CompanyRepository companyRepository;

    public AdminController(UserRepository userRepository, PasswordEncoder passwordEncoder, CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.companyRepository = companyRepository;
    }

    @ModelAttribute
    public void addAttribute(Model model) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("userName", principal.getFullName());
    }

    @GetMapping("/start")
    public String start(Model model) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", principal);

        return "/admin/start.jsp";
    }

    @GetMapping("/users")
    public String usersAll(Model model) {
        model.addAttribute("users", userRepository.findUserByRole("USER"));
        return "/user/userAll.jsp";
    }

    @GetMapping("/all")
    public String userAll(Model model) {

        model.addAttribute("users", userRepository.findAll());
        return "/user/userAll.jsp";
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
    @GetMapping("/info")
    public String info(Model model, HttpServletRequest request) {

        User user = userRepository.findById(Long.parseLong(request.getParameter("id"))).get();
        Hibernate.initialize(user.getAddresses());
        model.addAttribute("user", user);

        return "/user/userInfo.jsp";
    }
}
