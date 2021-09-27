package pl.coderslab.service;

import org.springframework.context.annotation.Configuration;
import pl.coderslab.entity.Addresses;
import pl.coderslab.entity.FaultOrder;
import pl.coderslab.entity.User;
import pl.coderslab.repository.FaultOrderRepository;

import java.util.List;
import java.util.Optional;

@Configuration
public class FaultOrderServic {

    private final FaultOrderRepository faultOrderRepository;

    public FaultOrderServic(FaultOrderRepository faultOrderRepository) {
        this.faultOrderRepository = faultOrderRepository;
    }


    public Boolean faultOrderVerification(User user,Long id){
        return faultOrderRepository.existsByClientAndId(user,id);

    }
    public Optional<FaultOrder> findFaultOrderByUserId(Long id){
        return faultOrderRepository.findById(id);
    }

    public FaultOrder findFaultOrderById(Long id){
        return faultOrderRepository.findById(id).get();

    }
    public List<FaultOrder> findAllFaultOrder(){
        return faultOrderRepository.findAll();
    }
    public FaultOrder addFaultOrder(FaultOrder faultOrder,User user){
        faultOrder.setClient(user);
        return faultOrderRepository.save(faultOrder);
    }
    public FaultOrder editFaultOrder(FaultOrder faultOrder, User user, Addresses addresses,String description){
        faultOrder.setClient(user);
        faultOrder.setDescription(description);
        faultOrder.setAddress(addresses);
        return faultOrderRepository.save(faultOrder);
    }

    public FaultOrder updateFaultOrder(FaultOrder faultOrder){
        FaultOrder faultOrder1 = faultOrderRepository.findById(faultOrder.getId()).get();
        faultOrder1.setStatus(faultOrder.getStatus());
        faultOrder1.setUser(faultOrder.getUser());
        return faultOrderRepository.save(faultOrder1);

    }

    public void deleteFaultOrder(FaultOrder faultOrder){
        faultOrderRepository.delete(faultOrder);
    }


}
