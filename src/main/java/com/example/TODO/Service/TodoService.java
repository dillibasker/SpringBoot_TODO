package com.example.TODO.Service;
import com.example.TODO.Repository.TodoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.example.TODO.models.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TodoService
{
    @Autowired
    private TodoRepository todoRepository;

    public Todo createTodo(Todo todo){

        return todoRepository.save(todo);
    }

    public Todo getTodoById(long id){
        return todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Todo not found"));
    }

    public List<Todo> getAll(){
        return todoRepository.findAll();
    }
    public Todo update(Todo todo){
        return todoRepository.save(todo);
    }

    public void deleteByID(Long id){
        todoRepository.delete(getTodoById(id));
    }

    public void deleTodo(Todo todo){
        todoRepository.delete(todo);
    }

    public Page<Todo> getAllTodoByPage(int page,int size){
        Pageable pageable= PageRequest.of(page,size);
        return todoRepository.findAll(pageable);
    }
}
