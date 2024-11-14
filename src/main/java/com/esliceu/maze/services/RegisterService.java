package com.esliceu.maze.services;

import com.esliceu.maze.dao.UserDAO;
import com.esliceu.maze.exceptions.NameTooShortException;
import com.esliceu.maze.exceptions.PasswordTooShortException;
import com.esliceu.maze.exceptions.UserExistsException;
import com.esliceu.maze.model.User;
import com.esliceu.maze.utils.Encryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class RegisterService {
 @Autowired
    UserDAO userDAO;
 @Autowired
    Encryptor encryptor;
 public void registerUser (String name, String username, String password) throws Exception{
     System.out.println("entra en el registerUser");
    boolean correctUsername = checkIfUsernameExists(username);
    boolean correctPassword = checkPasswordLength(password);
    boolean correctName = checkNameLength(name);

    if (!correctUsername) {
        System.out.println("entra en username incorrecto");
        throw new UserExistsException ("El username ya existe.");
    } else if (!correctPassword) {
        System.out.println("entra en password incorrecto");
        throw new PasswordTooShortException("La contraseña tiene que tener un mínimo de 5 carácteres.");
    } else if (!correctName) {
        System.out.println("entra en nombre incorrecto");
        throw new NameTooShortException("El nombre es demasiado corto, tiene que tener 6 carácteres.");
    }
     System.out.println("fuera del if crea usuario");

     User user = new User();
     user.setName(name);
     user.setUsername(username);
     String encryptPasw = encryptor.encryptString(password);
     user.setPassword(encryptPasw);
     userDAO.saveUser(user);
 }

    private boolean checkNameLength(String name) {
        if (name.length() < 6){
            return false;
        }
        return true;
    }

    private boolean checkPasswordLength(String password) {
        if (password.length() < 5){
         return false;
     }return true;
    }

    private boolean checkIfUsernameExists(String username) {
        User user = userDAO.findUserByUsername(username);
     if (user != null) {
         System.out.println("el usuario es nulo");
         return false;
     }
        return true;
    }
}
