package com.example.userservice.services;

import com.example.userservice.dtos.SendEmailDto;
import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import com.example.userservice.repositories.TokenRepository;
import com.example.userservice.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{


    private UserRepository userRepository;
    private TokenRepository tokenRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private KafkaTemplate<String,String> kafkaTemplate;

    private ObjectMapper objectMapper;

    public UserServiceImpl(UserRepository userRepository,TokenRepository tokenRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @Override
    public User signUp(String name, String email, String password) {

        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isPresent()){
            //TODO: throw an exception from here
            return null;
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));


        SendEmailDto sendEmailDto = new SendEmailDto();
        sendEmailDto.setFromEmail("tovenkatesh82@gmail.com");
        sendEmailDto.setToEmail(email);
        sendEmailDto.setSubject("Welcome");
        sendEmailDto.setBody("welcome to sclaer");

        String sendEmailDtoString = null;
        //sendEmailDtoString = objectMapper.writeValueAsString()




        return userRepository.save(user);
    }

    @Override
    public Token login(String email, String password) {

        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            //TODO-throw exception
            return null;
        }
        User user = userOptional.get();
        if(!bCryptPasswordEncoder.matches(password,user.getHashedPassword())){
            //TODO: Throw excpetion that password is wrong.
            return null;
        }
        Token token = createToken(user);
        return tokenRepository.save(token);
    }

    @Override
    public User validate(String tokenVale) {

        Optional<Token> optionalToken = tokenRepository.findByValueAndDeletedAndExpiryAtGreaterThan(
                tokenVale,false,new Date());

        if(optionalToken.isEmpty()){
            //TODO: throw an exception TokenInvalidException
            return null;
        }
        return optionalToken.get().getUser();
    }

    @Override
    public void logout(String Token) {

        Optional<Token> tokenOptional = tokenRepository.
                findByValueAndDeletedAndExpiryAtGreaterThan(Token, false, new Date());
        if(!tokenOptional.isPresent()){
            //Throw exception.
        }
            tokenOptional.get().setDeleted(true);
            tokenRepository.save(tokenOptional.get());
    }

    private Token createToken(User user){

        Token token = new Token();
        token.setUser(user);
        token.setValue(RandomStringUtils.randomAlphabetic(128));

        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH,30);

        Date dateAfter30Days = calendar.getTime();
        token.setExpiryAt(dateAfter30Days);
        token.setDeleted(false);
        return token;
    }
}
