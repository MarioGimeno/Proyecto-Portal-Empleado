package com.example.proyecto_portal_empleado.utils;

import com.example.proyecto_portal_empleado.requests.LoginRequest;
import com.example.proyecto_portal_empleado.responses.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/usuarios/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

}
