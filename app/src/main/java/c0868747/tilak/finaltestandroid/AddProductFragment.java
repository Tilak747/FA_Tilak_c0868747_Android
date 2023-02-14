package c0868747.tilak.finaltestandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import c0868747.tilak.finaltestandroid.databinding.FragmentAddProductBinding;
import c0868747.tilak.finaltestandroid.model.Product;
import c0868747.tilak.finaltestandroid.model.Vendor;

public class AddProductFragment extends Fragment {

    FragmentAddProductBinding binding;
    MainViewModel viewModel;
    boolean isEditing = false;
    Product productToEdit;
    long productId = -1;
    List<Vendor> registeredVendors = new ArrayList<>();
    Vendor selectedVendor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddProductBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.getProduct(productId).removeObservers(this);
        viewModel.getVendors().removeObservers(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedVendor = registeredVendors.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        viewModel.getVendors().observe(requireActivity(),vendors -> {
            registeredVendors = vendors;

            List<String> registeredVendorNames = new ArrayList<>();
            for(Vendor vendor:vendors){
                registeredVendorNames.add(vendor.getName());
            }

            ArrayAdapter<String> customAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1,registeredVendorNames);
            binding.spinner.setAdapter(customAdapter);


        });

        if(getArguments()!=null){
            productId = getArguments().getLong("id",-1);
            if(productId != -1){
                viewModel.getProduct(productId).observe(getViewLifecycleOwner(), product -> {
                    if(product != null){
                        binding.tielName.setText(product.getName());
                        binding.tielDescription.setText(product.getDescription());
                        binding.tielPrice.setText(""+product.getPrice());

                        String[] vendorArray = requireContext().getResources().getStringArray(R.array.vendor_names);
                        int position = Arrays.asList(vendorArray).indexOf(product.getVendorName());
//                        int position = registeredVendors.indexOf(product.getVendorName());
//                        selectedVendor = registeredVendors.get(position);
                        binding.spinner.setSelection(position);

                        viewModel.getVendor(product.getVendorName()).observe(getViewLifecycleOwner(),vendor ->{
                            if(vendor != null){
                                selectedVendor = vendor;
                            }
                        });
//                        selectedVendor = viewModel.getVendor(product.getVendorName());
//                    binding.spinner.setEnabled(false);
                        productToEdit = product;
                    }
                });
                isEditing = true;
                binding.toolbar.setTitle(R.string.fragment_update_product);
                binding.btnSave.setText(R.string.btn_update);
            }

        }




        binding.toolbar.setNavigationOnClickListener(v ->{
            NavHostFragment.findNavController(this).popBackStack();
        });

        binding.chipAddAnotherVendor.setOnClickListener(v->{
            NavHostFragment.findNavController(this).navigate(R.id.action_addProductFragment_to_addVendorFragment);
        });

        binding.btnSave.setOnClickListener(v->{

            binding.tilName.setError(null);
            binding.tilDesc.setError(null);
            binding.tilPrice.setError(null);

            String name = binding.tielName.getText().toString();
            if(name.isEmpty()){
                binding.tielName.setError("Name is required");
                return;
            }
            String desc = binding.tielDescription.getText().toString();
            if(desc.isEmpty()){
                binding.tielDescription.setError("Description is required");
                return;
            }
            String price = binding.tielPrice.getText().toString();
            if(price.isEmpty()){
                binding.tielPrice.setError("Price is required");
                return;
            }
            double priceValue = Double.parseDouble(price);
//            String vendorName = binding.spinner.getSelectedItem().toString();
            String vendorName = selectedVendor.getName();

            if(isEditing){
//                Vendor vendor = new Vendor();
//                Product product = new Product();

//                vendor.setId(productToEdit.getVendorId());
//                vendor.setName(vendorName);

//                long id = productToEdit.getId();
                productToEdit.setName(name);
                productToEdit.setDescription(desc);
                productToEdit.setPrice(priceValue);
                productToEdit.setVendorId(selectedVendor.getId());
                productToEdit.setVendorName(selectedVendor.getName());


                viewModel.updateProduct(productToEdit);
                Toast.makeText(requireContext(), "Updated", Toast.LENGTH_SHORT).show();


            }
            else{
                Product product = new Product();
                product.setName(name);
                product.setDescription(desc);
                product.setPrice(priceValue);
//                product.setVendorId(selectedVendor.getId());
                product.setVendorName(vendorName);
                viewModel.addProduct(product);
                Toast.makeText(requireContext(), "Product added", Toast.LENGTH_SHORT).show();
            }


            NavHostFragment.findNavController(this).popBackStack();

        });

    }
}
