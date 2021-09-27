package pl.coderslab.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.coderslab.entity.Addresses;
import pl.coderslab.entity.User;
import pl.coderslab.repository.AddressesRepository;

import java.util.List;

@Configuration
public class AddressesServic {
    private final AddressesRepository addressesRepository;

    public AddressesServic(AddressesRepository addressesRepository) {
        this.addressesRepository = addressesRepository;
    }

    public Addresses addAddress (Addresses addresses){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        addresses.setUser((User) principal);
        return addressesRepository.save(addresses);

    }

    public List<Addresses> findAddressesByUser(User user){
       return addressesRepository.findAddressesByUser(user);
    }
}
