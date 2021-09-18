package pl.coderslab.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.entity.Addresses;
import pl.coderslab.entity.User;
import pl.coderslab.repository.AddressesRepository;

import javax.validation.Valid;

@Controller
@RequestMapping("/addresses")
public class AddressesController {
    private final AddressesRepository addressesRepository;

    public AddressesController(AddressesRepository addressesRepository) {
        this.addressesRepository = addressesRepository;
    }

    @ModelAttribute
    public void addAttribute(Model model) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("userName", principal.getFullName());
    }

    @GetMapping("/add")
    public String addressesAdd(Model model) {
        model.addAttribute("addresses", new Addresses());
        return "/addresses/addressesAddForm.jsp";
    }

    @PostMapping("/add")
    public String addressesAddPost(@Valid Addresses addresses, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "/addresses/addressesAddForm.jsp";
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        addresses.setUser((User) principal);
        addressesRepository.save(addresses);
        return "redirect:/user/start";
    }
}
