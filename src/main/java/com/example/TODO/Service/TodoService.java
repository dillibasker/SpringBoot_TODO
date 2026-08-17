package com.example.TODO.Service;

import com.example.TODO.Repository.TodoRepository;
import com.example.TODO.models.Todo;
import com.example.TODO.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public Todo createTodo(Todo todo, User user) {

        todo.setUser(user);

        return todoRepository.save(todo);
    }

    public Todo getTodoById(long id, User user) {

        return todoRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Todo not found"));
    }

    public List<Todo> getAll(User user) {

        return todoRepository.findAllByUser(user);
    }

    public Todo update(Todo todo, User user) {

        Todo existingTodo = todoRepository
                .findByIdAndUser(todo.getId(), user)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        existingTodo.setTitle(todo.getTitle());
        existingTodo.setDescription(todo.getDescription());
        existingTodo.setIsCompleted(todo.getIsCompleted());

        return todoRepository.save(existingTodo);
    }

    public void deleteByID(Long id, User user) {

        Todo existingTodo = todoRepository
                .findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        todoRepository.delete(existingTodo);
    }

    public Page<Todo> getAllTodoByPage(int page, int size, User user) {

        Pageable pageable = PageRequest.of(page, size);

        return todoRepository.findAllByUser(user, pageable);
    }
}