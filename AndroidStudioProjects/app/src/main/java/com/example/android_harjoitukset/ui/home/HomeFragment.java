package com.example.android_harjoitukset.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.android_harjoitukset.GameActivity;
import com.example.android_harjoitukset.R;
import com.example.android_harjoitukset.SearchActivity;
import com.example.android_harjoitukset.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    public static final String TAG = "MainActivity";
    private Button helloButton;
    private Button gameButton;
    private Button searchButton;
    private EditText searchInput;

    private View helloTextView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        helloTextView = binding.hello;
        helloTextView.setVisibility(View.VISIBLE);

        helloButton = binding.helloButton;
        gameButton = binding.gameButton;
        searchButton = binding.searchButton;

        helloButton.setOnClickListener(this::handleOnClickEvents);
        gameButton.setOnClickListener(this::handleOnClickEvents);
        searchButton.setOnClickListener(this::handleOnClickEvents);

        searchInput = binding.searchText;


        return root;
    }

    private void handleOnClickEvents(View v) {
        if (v.getId() == R.id.helloButton) {
            Log.i(TAG, "User clicked the Start-button");
            if (helloTextView.getVisibility() == View.VISIBLE) {
                helloTextView.setVisibility(View.INVISIBLE);
            } else if (helloTextView.getVisibility() == View.INVISIBLE) {
                helloTextView.setVisibility(View.VISIBLE);

            }
        } else if (v.getId() == R.id.gameButton) {
            Log.i(TAG, "User clicked the Game-button");
            Intent i = new Intent(getActivity(), GameActivity.class);

            startActivity(i);
        } else if (v.getId() == R.id.searchButton) {
            Log.i(TAG, "User clicked the Search-button");
            Intent i = new Intent(getActivity(), SearchActivity.class);
            String searchText = searchInput.getText().toString();
            i.putExtra("searchTxt", searchText);
            startActivity(i);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}