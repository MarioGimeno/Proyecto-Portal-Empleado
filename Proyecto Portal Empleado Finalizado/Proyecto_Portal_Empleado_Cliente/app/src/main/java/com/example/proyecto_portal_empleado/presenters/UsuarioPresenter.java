package com.example.proyecto_portal_empleado.presenters;

import android.content.Context;

import com.example.proyecto_portal_empleado.contracts.ContractUser;
import com.example.proyecto_portal_empleado.entities.Usuario;
import com.example.proyecto_portal_empleado.model.UsuarioModel;
import com.example.proyecto_portal_empleado.responses.LoginResponse;

public class UsuarioPresenter implements ContractUser.Presenter, ContractUser.Model.OnLoginUserListener{
    private ContractUser.View view;
    private ContractUser.Model model;

    public UsuarioPresenter(ContractUser.View view, Context context){
        this.view = view;
        model = new UsuarioModel(this, context);
    }

    @Override
    public void login(String email, String password) {
        model.loginAPI(email, password, this);
    }

    @Override
    public void onFinished(LoginResponse user) {
        view.successLogin(user);
    }



    @Override
    public void onFailure(String err) {

    }
}
