package com.example.TODO.Service;
import com.example.TODO.Repository.UserRepository;
import com.example.TODO.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    @Autowired
    private UserRepository UserRepository;

    public User createUser(User User){

        return UserRepository.save(User);
    }

    public User getUserById(long id){
        return UserRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

}
