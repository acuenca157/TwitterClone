package org.izv.di.acl.twitterclone.view.activity.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.izv.di.acl.twitterclone.R;
import org.izv.di.acl.twitterclone.databinding.FragmentCreateTweetBinding;
import org.izv.di.acl.twitterclone.model.entity.Tweet;
import org.izv.di.acl.twitterclone.model.entity.User;
import org.izv.di.acl.twitterclone.view.activity.MainActivity;
import org.izv.di.acl.twitterclone.viewmodel.TweetViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateTweet extends Fragment {

    private @NonNull
    FragmentCreateTweetBinding binding;

    private User user;
    Button btnCreate, btnCancel;
    TweetViewModel tvm;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentCreateTweetBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize();
    }

    private void initialize() {
        user = ((MainActivity)getActivity()).getUser();
        btnCreate = binding.btnCreate;

        binding.etUrl.setOnFocusChangeListener((v, hasFocus) -> {
            String url;
                if(!binding.etUrl.getText().toString().isEmpty()) {
                    url = binding.etUrl.getText().toString();
                    binding.imageView.setVisibility(View.VISIBLE);
                    Glide.with(CreateTweet.this).asBitmap().load(binding.etUrl.getText().toString()).listener(requestListener).into(binding.imageView);
                }
                else
                    binding.imageView.setVisibility(View.GONE);
        });

        getViewModel();
        setCreateClickListener();
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setCreateClickListener(){
        btnCreate.setOnClickListener((View v) -> {
            Tweet tweet = createTweet();
            insertTweet(tweet);
        });
    }

    private void insertTweet(Tweet tweet) {
        tvm.insertTweet(tweet);
    }

    private Tweet createTweet(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();

        Tweet tweet = new Tweet();
        tweet.idUser = user.id;
        tweet.content = binding.etContent.getText().toString().trim();
        tweet.urlPic = binding.etUrl.getText().toString().trim();
        tweet.date = formatter.format(date).toString();
        return tweet;
    }

    private void getViewModel() {
        tvm = new ViewModelProvider(this).get(TweetViewModel.class);
        tvm.getLiveInsertTweetResult().observe(this.getViewLifecycleOwner(), id -> {
            if (id > 0){
                Log.v("xyzyx", "Se ha creado el tweet " + id);
                NavHostFragment.findNavController(this).navigate(R.id.action_createTweet_to_tweetsFragment);
            }
            else {
                alert("Error", "No se ha podido crear el tweet");
                Log.v("xyzyx", "Algo ha salido mal creando el tweet");
            }
        });
    }

    private void alert(String title, String description) {
        AlertDialog.Builder builder  = new AlertDialog.Builder(this.getContext());
        builder.setTitle(title)
                .setMessage(description);
        builder.create().show();
    }

    private RequestListener<Bitmap> requestListener = new RequestListener<Bitmap>() {
        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
            binding.imageView.setVisibility(View.GONE);
            return false;
        }

        @Override
        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
            // everything worked out, so probably nothing to do
            binding.imageView.setVisibility(View.VISIBLE);
            return false;
        }
    };
}