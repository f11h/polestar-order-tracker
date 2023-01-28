package io.f11h.polestarordertracker.persistence.repository;

import io.f11h.polestarordertracker.persistence.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    Optional<Order> getFirstByEnabledIsTrueAndLastExecutionIsNullOrLastExecutionIsBeforeOrderByLastExecutionAsc(LocalDateTime threshold);

}
