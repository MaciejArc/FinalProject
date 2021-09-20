package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.entity.Addresses;
import pl.coderslab.entity.User;

import java.util.List;

public interface AddressesRepository extends JpaRepository<Addresses, Long> {
    List<Addresses> findAddressesByUser(User user);
}
