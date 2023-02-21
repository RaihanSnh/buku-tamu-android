package com.example.bukutamu.ui.tamu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bukutamu.databinding.FragmentTamuBinding;


public class TamuFragment extends Fragment {

    private FragmentTamuBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TamuViewModel tamuViewModel =
                new ViewModelProvider(this).get(TamuViewModel.class);

        binding = FragmentTamuBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textTamu;
        tamuViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}