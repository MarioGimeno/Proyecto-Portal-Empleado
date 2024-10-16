package com.example.proyecto_portal_empleado.contracts;

import com.example.proyecto_portal_empleado.entities.Usuario;
import com.example.proyecto_portal_empleado.responses.LoginResponse;

public interface ContractUser {

    public interface View{

        void successLogin(LoginResponse user);

        void failureLogin(String err);
        // void failureLogin(MyException err);
    }
    public interface Presenter{
        // void login(String email, String pass);
        void login(String email, String password);
        // void login(ViewUser viewUser);
        // VIEW-ORM
        // BEANS-ENTITIES
        // MVP - MVVM
    }
    public interface Model{
        interface OnLoginUserListener{
            void onFinished(LoginResponse user);
            void onFailure(String err);
        }
        void loginAPI(String email, String password,
                      OnLoginUserListener onLoginUserListener);
    }
}
