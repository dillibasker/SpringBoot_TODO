package com.example.TODO;

import com.example.TODO.models.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface TodoRepository extends JpaRepository<Todo,Long> {

}
