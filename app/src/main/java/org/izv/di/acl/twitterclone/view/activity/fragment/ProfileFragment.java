package org.izv.di.acl.twitterclone.view.activity.fragment;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.izv.di.acl.twitterclone.R;
import org.izv.di.acl.twitterclone.databinding.FragmentProfileBinding;
import org.izv.di.acl.twitterclone.model.entity.User;
import org.izv.di.acl.twitterclone.model.entity.UserTweet;
import org.izv.di.acl.twitterclone.view.activity.MainActivity;
import org.izv.di.acl.twitterclone.view.adapter.TweetAdapter;
import org.izv.di.acl.twitterclone.viewmodel.TweetViewModel;
import org.izv.di.acl.twitterclone.viewmodel.UserViewModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private @NonNull
    FragmentProfileBinding binding;
    User activeUser;
    User searchUser;

    TweetViewModel tvm;
    UserViewModel uvm;

    ImageView ivBanner;
    CircleImageView ivPFP;
    TextView tvUsername, tvUserDescription;
    RecyclerView rvTweets;

    ActivityResultLauncher<String> mGetContent;

    private AppBarConfiguration appBarConfiguration;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        Glide.with(getActivity())
                                .load(uri)
                                .into(binding.ivPFP);
                        updateUser("", uri);
                    }
                });
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchUser = getArguments().getParcelable("searchUser");
        initialize();
    }

    private void initialize() {
        ((MainActivity)getActivity()).getSupportActionBar().hide();

        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getViewModel();
        activeUser = ((MainActivity)getActivity()).getUser();

        binding.collapsingbar.setTitle("@" + searchUser.username);

        /*NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController((MainActivity)getActivity(), navController, appBarConfiguration);*/

        Toolbar toolbar = binding.toolbar;
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        ivBanner = binding.ivBanner;
        ivPFP = binding.ivPFP;
        tvUsername = binding.tvProfileName;
        tvUserDescription = binding.tvUserDescription;
        rvTweets = binding.rvUserTweets;
        rvTweets.setLayoutManager(new LinearLayoutManager(this.getContext()));
        TweetAdapter tweetAdapter = new TweetAdapter(this.getContext(), activeUser.id, false);
        rvTweets.setAdapter(tweetAdapter);
        LiveData<List<UserTweet>> listaUserTweet = tvm.getTweetsFromUser(searchUser.id);
        listaUserTweet.observe(this.getViewLifecycleOwner(), userTweets -> {
            tweetAdapter.setTweetList(userTweets);
        });

        Glide.with(this).load(searchUser.urlUserPic).into(ivPFP);
        Glide.with(this).asBitmap().load(searchUser.urlUserBanner).listener(requestListener).into(ivBanner);
        tvUsername.setText(searchUser.username);
        tvUserDescription.setText(searchUser.description);

        if (searchUser.id == activeUser.id){
            ivPFP.setOnClickListener((View v) -> {
                mGetContent.launch("image/*");
            });

            tvUserDescription.setOnClickListener((View v) -> {
                tvUserDescription.setVisibility(View.GONE);
                binding.etDesc.setVisibility(View.VISIBLE);
                binding.etDesc.requestFocus();

                binding.etDesc.setOnFocusChangeListener((vw, hasFocus) -> {
                    tvUserDescription.setVisibility(View.VISIBLE);
                    binding.etDesc.setVisibility(View.GONE);
                    updateUser(binding.etDesc.getText().toString(), null);

                });
            });



        }
    }

    private void updateUser(String description, Uri uriPic){
        User newUser = new User();
        newUser.id = activeUser.id;
        if (uriPic != null)
            newUser.urlUserPic = uriPic.toString();
        else
            newUser.urlUserPic = activeUser.urlUserPic;

        if (!description.isEmpty())
            newUser.description = description;
        else
            newUser.description = activeUser.description;
        newUser.username = activeUser.username;
        newUser.urlUserBanner = activeUser.urlUserBanner;
        newUser.password = activeUser.password;

        uvm.updateUser(newUser);

        ((MainActivity)getActivity()).setUser(newUser);
        initialize();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        ((MainActivity)getActivity()).getSupportActionBar().show();
    }


    private void getViewModel() {
        tvm = new ViewModelProvider(this).get(TweetViewModel.class);
        uvm = new ViewModelProvider(this).get(UserViewModel.class);
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
            Glide.with(ProfileFragment.this).load("https://th.bing.com/th/id/R.0a2236193afacc014d1099da17fa4eba?rik=JDchHCr12t7ANQ&pid=ImgRaw&r=0").into(ivBanner);
            return false;
        }

        @Override
        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
            // everything worked out, so probably nothing to do
            return false;
        }
    };
}