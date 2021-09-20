package pl.coderslab.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.entity.FaultOrder;
import pl.coderslab.entity.User;
import pl.coderslab.repository.AddressesRepository;
import pl.coderslab.repository.FaultOrderRepository;
import pl.coderslab.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
@RequestMapping("/faultOrder")
public class FaultOrderController {
    private final FaultOrderRepository faultOrderRepository;
    private final AddressesRepository addressesRepository;
    private final UserRepository userRepository;

    public FaultOrderController(FaultOrderRepository faultOrderRepository, AddressesRepository addressesRepository, UserRepository userRepository) {

        this.faultOrderRepository = faultOrderRepository;
        this.addressesRepository = addressesRepository;
        this.userRepository = userRepository;
    }

    @ModelAttribute
    public void addAttribute(Model model) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("userName", principal.getFullName());
    }

    @GetMapping("/add")
    public String faultOrderAdd(Model model, HttpServletRequest request) {

        if (request.getParameter("id").isEmpty()) {
            model.addAttribute("faultOrder", new FaultOrder());

        } else {
            String id = request.getParameter("id");
            model.addAttribute("faultOrder", faultOrderRepository.findById(Long.parseLong(id)));

        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("addresses", addressesRepository.findAddressesByUser((User) principal));
        return "/faultOrder/faultOrderAddForm.jsp";
    }

    @PostMapping("/add")
    public String faultOrderAddPost(@Valid FaultOrder faultOrder, BindingResult result, Model model, HttpServletRequest request) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (result.hasErrors()) {
            model.addAttribute("addresses", addressesRepository.findAddressesByUser((User) principal));
            return "/faultOrder/faultOrderAddForm.jsp";
        }
        if (request.getParameter("id").isEmpty()) {
            faultOrder.setClient((User) principal);
            faultOrderRepository.save(faultOrder);

        } else {
            FaultOrder faultOrder1 = faultOrderRepository.findById(faultOrder.getId()).get();
            faultOrder1.setClient((User) principal);
            faultOrder1.setAddress(faultOrder.getAddress());
            faultOrder1.setDescription(faultOrder.getDescription());
            faultOrderRepository.save(faultOrder1);
        }
        return "redirect:/user/start";

    }

    @GetMapping("/all")
    public String faultOrderAll(Model model) {

        model.addAttribute("faultOrder", faultOrderRepository.findAll());
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", principal);
        return "/faultOrder/faultOrderAll.jsp";
    }

    @GetMapping("/update")
    public String faultOrderUpdate(Model model, HttpServletRequest request) {

        String id = request.getParameter("id");
        model.addAttribute("faultOrder", faultOrderRepository.findById(Long.parseLong(id)).get());
        model.addAttribute("users", userRepository.findUserByRole("ROLE_WORKER"));
        return "/faultOrder/faultOrderUpdate.jsp";
    }

    @PostMapping("/update")
    public String faultOrderUpdatePost(@Valid FaultOrder faultOrder, BindingResult result, Model model, HttpServletRequest request) {

        if (result.hasErrors()) {
            String id = request.getParameter("id");
            model.addAttribute("faultOrder", faultOrderRepository.findById(Long.parseLong(id)).get());
            model.addAttribute("users", userRepository.findUserByRole("USER"));
            return "/faultOrder/faultOrderUpdate.jsp";

        }
        FaultOrder faultOrder1 = faultOrderRepository.findById(faultOrder.getId()).get();
        faultOrder1.setStatus(faultOrder.getStatus());
        faultOrder1.setUser(faultOrder.getUser());
        faultOrderRepository.save(faultOrder1);

        return "redirect:/admin/start";
    }

    @GetMapping("/delete")
    public String deleteFaultOrder(HttpServletRequest request) {

        FaultOrder faultOrder = faultOrderRepository.findById(Long.parseLong(request.getParameter("id"))).get();
        faultOrderRepository.delete(faultOrder);
        return "redirect:/faultOrder/all";
    }
}