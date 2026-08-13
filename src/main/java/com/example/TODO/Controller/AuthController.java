package com.example.TODO.Controller;

import com.example.TODO.Repository.UserRepository;
import com.example.TODO.Service.UserService;
import com.example.TODO.Utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.TODO.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final PasswordEncoder passwordencoder;
    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Map<String,String> body){

        String email=body.get("email");
        String password=passwordencoder.encode(body.get("password"));
        if(userRepository.findByEmail(email).isPresent()){
            return new ResponseEntity<>("Email Already Exist", HttpStatus.CONFLICT);
        }

        userService.createUser(User.builder().email(email).password(password).build());
        return new ResponseEntity<>("Succesfully Registered",HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String,String> body){
        String email=body.get("email");
        String password=body.get("password");

        var userOptional=userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            return new ResponseEntity<>("User not registered",HttpStatus.UNAUTHORIZED);
        }
        User user=userOptional.get();

        if(!passwordencoder.matches(password, user.getPassword())){
            return new ResponseEntity<>("Invalid user",HttpStatus.UNAUTHORIZED);
        }

        String token=jwtUtil.generateToken(email);
        return ResponseEntity.ok(Map.of("token",token));
    }
}
