package c0868747.tilak.finaltestandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import c0868747.tilak.finaltestandroid.databinding.FragmentAddVendorBinding;
import c0868747.tilak.finaltestandroid.model.Vendor;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddVendorFragment extends Fragment {

    FragmentAddVendorBinding binding;
    MainViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddVendorBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        binding.toolbar.setNavigationOnClickListener(v -> {
            goBack();
        });

        binding.btnSave.setOnClickListener(v->{

            binding.tilLat.setError(null);
            binding.tilLng.setError(null);

            String lat = binding.tielLat.getText().toString();
            if(lat.isEmpty()){
                binding.tilLng.setError("Latitude required");
                return;
            }
            String lng = binding.tielLng.getText().toString();
            if(lng.isEmpty()){
                binding.tilLng.setError("Longitude required");
                return;
            }
            double latValue = Double.parseDouble(lat);
            double lngValue = Double.parseDouble(lng);
            String name = binding.spinner.getSelectedItem().toString();
            Vendor vendor = new Vendor(name,latValue,lngValue);
            viewModel.addVendor(vendor);
            goBack();

        });
    }

    private void goBack(){
        NavHostFragment.findNavController(this).popBackStack();
    }
}
