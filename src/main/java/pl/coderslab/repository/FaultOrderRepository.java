package pl.coderslab.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.entity.FaultOrder;
import pl.coderslab.entity.User;

import java.util.List;
import java.util.Optional;

public interface FaultOrderRepository extends JpaRepository<FaultOrder, Long> {
    List<FaultOrder> findFaultOrdersByClient(User user);
}