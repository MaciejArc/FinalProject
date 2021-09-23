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
import pl.coderslab.service.FaultOrderServic;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
@RequestMapping("/faultOrder")
public class FaultOrderController {
    private final FaultOrderRepository faultOrderRepository;
    private final AddressesRepository addressesRepository;
    private final UserRepository userRepository;

    private final FaultOrderServic faultOrderServic;

    public FaultOrderController(FaultOrderRepository faultOrderRepository, AddressesRepository addressesRepository, UserRepository userRepository, FaultOrderServic faultOrderServic) {
        this.faultOrderRepository = faultOrderRepository;
        this.addressesRepository = addressesRepository;
        this.userRepository = userRepository;
        this.faultOrderServic = faultOrderServic;
    }

    @ModelAttribute
    public void addAttribute(Model model) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("userName", principal.getFullName());
    }

    @GetMapping("/add")
    public String faultOrderAdd(Model model, HttpServletRequest request) {

       User principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (request.getParameter("id").isEmpty()) {
            model.addAttribute("faultOrder", new FaultOrder());

        } else {
            Long id = Long.parseLong(request.getParameter("id"));
           if(faultOrderServic.faultOrderVerification(principal,id)){
               model.addAttribute("faultOrder", faultOrderServic.findFaultOrderByUserId(id));
           }else {
               return "redirect:/user/start";
           }

        }


        model.addAttribute("addresses", addressesRepository.findAddressesByUser((User) principal));
        return "/faultOrder/faultOrderAddForm.jsp";
    }

    @PostMapping("/add")
    public String faultOrderAddPost(@Valid FaultOrder faultOrder, BindingResult result, Model model, HttpServletRequest request) {

        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (result.hasErrors()) {
            model.addAttribute("addresses", addressesRepository.findAddressesByUser(principal));
            return "/faultOrder/faultOrderAddForm.jsp";
        }
        if (request.getParameter("id").isEmpty()) {
            faultOrder.setClient(principal);
            faultOrderRepository.save(faultOrder);

        } else {
            FaultOrder faultOrder2 = faultOrderServic.findFaultOrderById(faultOrder.getId());
            faultOrderServic.editFaultOrder(faultOrder2,principal,faultOrder.getAddress(),faultOrder.getDescription());

        }
        return "redirect:/user/start";

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