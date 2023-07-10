package com.fooddelivery.backend.Service;

import com.fooddelivery.backend.Models.Users;
import com.fooddelivery.backend.Models.Request.UserSignIn;
import com.fooddelivery.backend.Models.Request.UserSignUp;
import com.fooddelivery.backend.Models.Response.UserResponse;

public interface UserService {
    UserResponse getUserResById(Long id);
    UserResponse getUseResByUsername(String username);
    Users getUserById(Long id);
    Users getUserByUsername(String username);
    UserResponse saveUser(UserSignUp userSignup);
    UserResponse signIn(UserSignIn userSignIn);
    UserResponse loadAuthUserRes();
    Users getAuthUser();
}
