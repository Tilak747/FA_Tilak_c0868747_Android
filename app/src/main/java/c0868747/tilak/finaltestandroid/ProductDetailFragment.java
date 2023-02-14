package c0868747.tilak.finaltestandroid;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import c0868747.tilak.finaltestandroid.databinding.FragmentProductDetailBinding;
import c0868747.tilak.finaltestandroid.model.Vendor;

public class ProductDetailFragment extends Fragment implements OnMapReadyCallback {

    long productId = -1;

    FragmentProductDetailBinding binding;
    MainViewModel viewModel;
    GoogleMap googleMap = null;
    Vendor vendor = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProductDetailBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        productId = getArguments().getLong("id",-1);

        binding.toolbar.setNavigationOnClickListener(v->{
            NavHostFragment.findNavController(this).popBackStack();
        });

        SupportMapFragment map = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        map.getMapAsync(this);

        loadData();
    }

    private void loadData(){
        if(viewModel.getProduct(productId).hasObservers()){
            viewModel.getProduct(productId).removeObservers(getViewLifecycleOwner());
        }
        viewModel.getProduct(productId).observe(getViewLifecycleOwner(),product -> {

            if(product!=null){

                binding.tvDetail.setText("Name : "+product.getName() + "\n" +
                        "Description :\n" + product.getDescription() + "\n\n" +
                        "Price : "+product.getPrice() + "\n\n" +
                        "Vendor Name: "+product.getVendorName()
                );

                viewModel.getVendor(product.getVendorName()).observe(getViewLifecycleOwner(),vendor -> {
                    this.vendor = vendor;
                    if(vendor != null){

                        LatLng vendorLocation = new LatLng(vendor.getLat(),vendor.getLng());
                        if(googleMap!=null){
                            googleMap.clear();
                            googleMap.addMarker(new MarkerOptions().position(vendorLocation).title(vendor.getName()));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(vendorLocation,10));
                        }

                    }
                });
            }


        });
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;

    }
}
