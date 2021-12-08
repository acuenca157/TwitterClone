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
import org.izv.di.acl.twitterclone.databinding.FragmentFirstBinding;
import org.izv.di.acl.twitterclone.view.activity.MainActivity;
import org.izv.di.acl.twitterclone.viewmodel.UserViewModel;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private UserViewModel uvm;
    private EditText etUsername, etPassword;
    String user, pass;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize();
    }

    private void initialize() {
        etUsername = binding.inputUser;
        etPassword = binding.inputPassword;

        getViewModel();
        defineLoginListener();

        binding.tvJoinAccount.setOnClickListener((View v)->{
            NavHostFragment.findNavController(this).navigate(R.id.action_FirstFragment_to_SecondFragment);
        });
    }

    private void defineLoginListener() {
        Button btn = binding.btnAction;
        btn.setOnClickListener((View v) -> {
            checkUser();
        });
    }

    private void checkUser() {
        user = etUsername.getText().toString().trim();
        pass = etPassword.getText().toString();
        uvm.getUserById(user, pass);
    }

    private void getViewModel() {
        uvm = new ViewModelProvider(this).get(UserViewModel.class);
        uvm.getLiveUserSearchResult().observe(this.getViewLifecycleOwner(), user -> {
            if (user != null){
                Log.v("xyzyx", "Iniciamos sesion con " + user.username);
                ((MainActivity)getActivity()).setUser(user);
                ((MainActivity)getActivity()).createTopbar();
                NavHostFragment.findNavController(this).navigate(R.id.action_FirstFragment_to_tweetsFragment);
            }
            else
                alert("Error", "User and password does not match.");

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