package com.example.demo.src.user.repository;

import com.example.demo.src.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.example.demo.common.entity.BaseEntity.State;

public interface UserRepository extends UserRepositoryCustom, JpaRepository<User, Long> {

    Optional<User> findByIdAndState(Long id, State state);

    Optional<User> findByEmailAndState(String email, State state);

    List<User> findAllByEmailAndState(String email, State state);

    List<User> findAllByState(State state);

    List<User> findAllByIdInAndState(Set<Long> userIds, State state);

    @Query("SELECT u FROM User u WHERE u.state = 'ACTIVE' AND u.id IN :userIds")
    List<User> findByIds(List<Long> userIds);
}
