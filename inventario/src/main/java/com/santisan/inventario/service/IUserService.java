package com.santisan.inventario.service;

import com.santisan.inventario.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {
    UserDTO createUser(UserDTO newUser);
    UserDTO updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);
}
