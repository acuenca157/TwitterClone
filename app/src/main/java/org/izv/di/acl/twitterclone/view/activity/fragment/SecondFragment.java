package org.izv.di.acl.twitterclone.view.activity.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import org.izv.di.acl.twitterclone.R;
import org.izv.di.acl.twitterclone.databinding.FragmentSecondBinding;
import org.izv.di.acl.twitterclone.model.entity.User;
import org.izv.di.acl.twitterclone.viewmodel.UserViewModel;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private UserViewModel uvm;

    private EditText etUsername, etPassword, etConfirmPassword;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize();
    }

    private void defineRegisterListener(){
        Button btnRegister = binding.btnAction;
        btnRegister.setOnClickListener((View v) -> {
            if (etUsername.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty() || etConfirmPassword.getText().toString().isEmpty()){
                alert("Error", "All the fields must be completed.");
                return;
            }
            User user = getUser();
            if (user != null){
                createUser(user);
            }
        });
    }

    private void createUser(User user) {
        uvm.insertUser(user);
    }

    private User getUser() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();
        binding.tvJoinAccount.setOnClickListener((View v)-> {
            NavHostFragment.findNavController(this).navigate(R.id.action_SecondFragment_to_FirstFragment);
        });
        if (password.equals(confirmPassword)){
            User user = new User();
            user.username = username;
            user.password = password;
            user.description = "Hello there! I'm usign Twitter!";
            user.urlUserPic = "https://www.enriquedans.com/wp-content/uploads/2017/12/Twitter-default-avatar-2017.jpg";
            return user;
        } else {
            alert("Error", "No coinciden usuario y contraseña");
            return null;
        }
    }

    private void initialize() {
        etUsername = binding.inputUser;
        etPassword = binding.inputPassword;
        etConfirmPassword = binding.inputPasswordConfirm;

        getViewModel();
        defineRegisterListener();
    }

    private void getViewModel() {
        uvm = new ViewModelProvider(this).get(UserViewModel.class);
        uvm.getLiveInsertUserResult().observe(this.getViewLifecycleOwner(), id -> {
            if (id > 0){
                NavHostFragment.findNavController(this).navigate(R.id.action_SecondFragment_to_FirstFragment);
                Log.v("xyzyx", "Usuario " + id + " creado con exito");
            }
            else {
                alert("Error", "No se ha podido crear el usuario, prueba con otro nombre de usuario");
                Log.v("xyzyx", "Algo ha salido mal creando al usuario");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void alert(String title, String description) {
        AlertDialog.Builder builder  = new AlertDialog.Builder(this.getContext());
        builder.setTitle(title)
                .setMessage(description);
        builder.create().show();
    }

}