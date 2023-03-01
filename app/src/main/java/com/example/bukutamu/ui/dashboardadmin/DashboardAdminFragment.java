package com.example.bukutamu.ui.dashboardadmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.bukutamu.R;
import com.example.bukutamu.databinding.FragmentDashboardBinding;
import com.example.bukutamu.ui.dashboard.DashboardViewModel;
import com.example.bukutamu.ui.listtamu.ListTamuFragment;
import com.example.bukutamu.ui.tamu.TamuFragment;

public class DashboardAdminFragment extends Fragment {

    Button view;

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardAdminViewModel DashboardAdminViewModel =
                new ViewModelProvider(this).get(DashboardAdminViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        view = root.findViewById(R.id.view_listtamu);

        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                TamuFragment listtamuFragment = new TamuFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment_content_main, listtamuFragment);
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