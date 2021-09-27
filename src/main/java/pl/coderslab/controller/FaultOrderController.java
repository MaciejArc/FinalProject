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
import pl.coderslab.service.AddressesServic;
import pl.coderslab.service.FaultOrderServic;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
@RequestMapping("/faultOrder")
public class FaultOrderController {

    private final UserRepository userRepository;
    private final AddressesServic addressesServic;
    private final FaultOrderServic faultOrderServic;

    public FaultOrderController(UserRepository userRepository, AddressesServic addressesServic, FaultOrderServic faultOrderServic) {
        this.userRepository = userRepository;
        this.addressesServic = addressesServic;
        this.faultOrderServic = faultOrderServic;
    }

    @ModelAttribute
    public void addAttribute(Model model) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("userName", principal.getFullName());
    }

    @GetMapping("/add")
    public String faultOrderAdd(Model model, HttpServletRequest request) {

        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (request.getParameter("id").isEmpty()) {
            model.addAttribute("faultOrder", new FaultOrder());

        } else {
            Long id = Long.parseLong(request.getParameter("id"));
            if (faultOrderServic.faultOrderVerification(principal, id)) {
                model.addAttribute("faultOrder", faultOrderServic.findFaultOrderByUserId(id));
            } else {
                return "redirect:/user/start";
            }

        }


        model.addAttribute("addresses", addressesServic.findAddressesByUser(principal));
        return "/faultOrder/faultOrderAddForm.jsp";
    }

    @PostMapping("/add")
    public String faultOrderAddPost(@Valid FaultOrder faultOrder, BindingResult result, Model model, HttpServletRequest request) {

        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (result.hasErrors()) {
            model.addAttribute("addresses", addressesServic.findAddressesByUser(principal));
            return "/faultOrder/faultOrderAddForm.jsp";
        }
        if (request.getParameter("id").isEmpty()) {
            faultOrderServic.addFaultOrder(faultOrder, principal);

        } else {
            FaultOrder faultOrder2 = faultOrderServic.findFaultOrderById(faultOrder.getId());
            faultOrderServic.editFaultOrder(faultOrder2, principal, faultOrder.getAddress(), faultOrder.getDescription());

        }
        return "redirect:/user/start";

    }

    @GetMapping("/update")
    public String faultOrderUpdate(Model model, HttpServletRequest request) {

        String id = request.getParameter("id");
        model.addAttribute("faultOrder", faultOrderServic.findFaultOrderById(Long.parseLong(id)));
        model.addAttribute("users", userRepository.findUserByRole("ROLE_WORKER"));
        return "/faultOrder/faultOrderUpdate.jsp";
    }

    @PostMapping("/update")
    public String faultOrderUpdatePost(@Valid FaultOrder faultOrder, BindingResult result, Model model, HttpServletRequest request) {

        if (result.hasErrors()) {
            String id = request.getParameter("id");
            model.addAttribute("faultOrder", faultOrderServic.findFaultOrderById(Long.parseLong(id)));
            model.addAttribute("users", userRepository.findUserByRole("ROLE_WORKER"));
            return "/faultOrder/faultOrderUpdate.jsp";

        }
        faultOrderServic.updateFaultOrder(faultOrder);


        return "redirect:/admin/start";
    }

    @GetMapping("/delete")
    public String deleteFaultOrder(HttpServletRequest request) {

        FaultOrder faultOrder = faultOrderServic.findFaultOrderById(Long.parseLong(request.getParameter("id")));
        faultOrderServic.deleteFaultOrder(faultOrder);
        return "redirect:/faultOrder/all";
    }
}