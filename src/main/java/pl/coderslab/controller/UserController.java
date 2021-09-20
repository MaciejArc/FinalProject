package pl.coderslab.controller;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.entity.User;
import pl.coderslab.repository.CompanyRepository;
import pl.coderslab.service.UserServic;
import javax.validation.Valid;


@Controller
@RequestMapping("/user")
public class UserController {


    private final CompanyRepository companyRepository;
    private final UserServic userServic;

    public UserController(CompanyRepository companyRepository, UserServic userServic) {
        this.companyRepository = companyRepository;
        this.userServic = userServic;
    }

    @ModelAttribute
    public void addAttribute(Model model) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("userName", principal.getFullName());
        model.addAttribute("companys", companyRepository.findAll());

    }


    @GetMapping("/add")
    public String clientAdd(Model model) {

        model.addAttribute("user", new User());
        return "/user/clientAddForm.jsp";
    }

    @PostMapping("/add")
    public String clientAddPost(@Valid User user, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "/user/clientAddForm.jsp";
        }

        if(userServic.emailExist(user.getEmail())){
            model.addAttribute("error", "Użytkownik o podanym adresie email istnieje!");
            return "/user/clientAddForm.jsp";
        }
        userServic.registryNewAccount(user);
        return "redirect:/user/start";

    }



    @GetMapping("/start")
    public String start(Model model) {

        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("order", userServic.findFaultOrderByClient(principal));
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