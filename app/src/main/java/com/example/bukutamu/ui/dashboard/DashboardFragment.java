package com.example.bukutamu.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.bukutamu.R;
import com.example.bukutamu.databinding.FragmentDashboardBinding;
import com.example.bukutamu.ui.tamu.TamuFragment;


public class DashboardFragment extends Fragment {

    ImageView arrow;

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel DashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        arrow = root.findViewById(R.id.image_arrow);

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment tamuFragment = new TamuFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment_content_main, tamuFragment);
                transaction.addToBackStack(null);
                transaction.commit();
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