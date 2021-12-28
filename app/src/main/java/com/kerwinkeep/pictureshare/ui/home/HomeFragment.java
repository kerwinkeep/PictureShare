package com.kerwinkeep.pictureshare.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kerwinkeep.pictureshare.R;
import com.kerwinkeep.pictureshare.bean.Picture;
import com.kerwinkeep.pictureshare.databinding.FragmentHomeBinding;
import com.kerwinkeep.pictureshare.ui.adapter.PictureAdapter;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    PictureAdapter pictureAdapter = new PictureAdapter(getContext());

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final RecyclerView recyclerView = binding.recyclerviewIndex;
        recyclerView.setAdapter(pictureAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        homeViewModel.getLivePictureList().observe(getViewLifecycleOwner(), new Observer<List<Picture>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Picture> pictures) {
                pictureAdapter.setPictureList(pictures);
                pictureAdapter.notifyDataSetChanged();
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}