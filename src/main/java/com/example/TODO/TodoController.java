package com.example.TODO;

import com.example.TODO.models.Todo;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todo")
public class TodoController {


    @Autowired
    private TodoService todoService;
    @GetMapping("/get")
    String getTodo(){
        return "todo";
    }
    @PostMapping("/create")
    ResponseEntity<Todo> createUser(@RequestBody Todo todo){
        return new ResponseEntity<>(todoService.createTodo(todo), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    ResponseEntity<Todo> getTodoById(@PathVariable long id){
        try {
            Todo Created = todoService.getTodoById(id);
            return new ResponseEntity<>(Created, HttpStatus.OK);
        }catch (RuntimeException exception){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/page")
    ResponseEntity<Page<Todo>> getPageTodo(@RequestParam int page,@RequestParam int size){
        return new ResponseEntity<>(todoService.getAllTodoByPage(page,size),HttpStatus.OK);
    }

    @GetMapping("/todos")
    ResponseEntity<List<Todo>> getTodos(){
        return new ResponseEntity<List<Todo>>(todoService.getAll(),HttpStatus.OK);
    }


    @PutMapping("/update")
    ResponseEntity<Todo> updateTodo(@RequestBody Todo todo){
        return new ResponseEntity<>(todoService.update(todo),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    void deleteTodoByID(@PathVariable long id){
        todoService.deleteByID(id);
    }
}
