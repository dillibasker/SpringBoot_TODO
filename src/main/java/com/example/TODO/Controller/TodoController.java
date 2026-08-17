package com.example.TODO.Controller;

import com.example.TODO.Repository.UserRepository;
import com.example.TODO.Service.TodoService;
import com.example.TODO.models.Todo;
import com.example.TODO.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @Autowired
    private UserRepository userRepository;


    private User getLoggedInUser(Authentication authentication) {

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


    @GetMapping("/get")
    public String getTodo() {
        return "todo";
    }


    @PostMapping("/create")
    public ResponseEntity<Todo> createTodo(
            @RequestBody Todo todo,
            Authentication authentication
    ) {

        User user = getLoggedInUser(authentication);

        Todo createdTodo = todoService.createTodo(todo, user);

        return new ResponseEntity<>(
                createdTodo,
                HttpStatus.CREATED
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(
            @PathVariable long id,
            Authentication authentication
    ) {

        try {

            User user = getLoggedInUser(authentication);

            Todo todo = todoService.getTodoById(id, user);

            return new ResponseEntity<>(
                    todo,
                    HttpStatus.OK
            );

        } catch (RuntimeException exception) {

            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }
    }


    @GetMapping("/page")
    public ResponseEntity<Page<Todo>> getPageTodo(
            @RequestParam int page,
            @RequestParam int size,
            Authentication authentication
    ) {

        User user = getLoggedInUser(authentication);

        return new ResponseEntity<>(
                todoService.getAllTodoByPage(page, size, user),
                HttpStatus.OK
        );
    }


    @GetMapping("/todos")
    public ResponseEntity<List<Todo>> getTodos(
            Authentication authentication
    ) {

        User user = getLoggedInUser(authentication);

        return new ResponseEntity<>(
                todoService.getAll(user),
                HttpStatus.OK
        );
    }


    @PutMapping("/update")
    public ResponseEntity<Todo> updateTodo(
            @RequestBody Todo todo,
            Authentication authentication
    ) {

        User user = getLoggedInUser(authentication);

        return new ResponseEntity<>(
                todoService.update(todo, user),
                HttpStatus.OK
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodoByID(
            @PathVariable long id,
            Authentication authentication
    ) {

        User user = getLoggedInUser(authentication);

        todoService.deleteByID(id, user);

        return ResponseEntity.noContent().build();
    }
}