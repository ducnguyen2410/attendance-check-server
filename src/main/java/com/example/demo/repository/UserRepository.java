package com.example.demo.repository;

import com.example.demo.entity.Contract;
import com.example.demo.entity.Position;
import com.example.demo.entity.PositionLevel;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public abstract User getUserByUsername(String username);
    public abstract User findByUsername(String username);
    public abstract List<User> findUserByPosition(Position position);
    public abstract List<User> findUserByPositionLevel(PositionLevel positionLevel);
    public abstract List<User> findUserByContract(Contract contract);
}
