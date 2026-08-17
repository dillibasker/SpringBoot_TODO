package com.example.TODO.Repository;

import com.example.TODO.models.Todo;
import com.example.TODO.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findAllByUser(User user);

    Optional<Todo> findByIdAndUser(Long id, User user);

    Page<Todo> findAllByUser(User user, Pageable pageable);
}