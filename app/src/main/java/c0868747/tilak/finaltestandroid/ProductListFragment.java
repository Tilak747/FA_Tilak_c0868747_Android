package c0868747.tilak.finaltestandroid;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import c0868747.tilak.finaltestandroid.adapter.ProductListAdapter;
import c0868747.tilak.finaltestandroid.databinding.FragmentProductListBinding;
import c0868747.tilak.finaltestandroid.model.Product;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProductListFragment extends Fragment {

    FragmentProductListBinding binding;
    ProductListAdapter adapter;
    MainViewModel viewModel;
    String lastSearch = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(binding == null){
            binding = FragmentProductListBinding.inflate(inflater,container,false);
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ProductListAdapter.ProductItemListener listener = new ProductListAdapter.ProductItemListener() {
            @Override
            public void viewProduct(Product product) {
                Bundle bundle = new Bundle();
                bundle.putInt("id",product.getId());
                Navigation.findNavController(requireActivity(),R.id.fragContainerView)
                        .navigate(R.id.action_productListFragment_to_productDetailFragment,bundle);
            }

            @Override
            public void showMenu(Product product) {
                BsItemOptions.ActionProvider provider = new BsItemOptions.ActionProvider() {
                    @Override
                    public void edit() {
                        editProduct(product);
                    }

                    @Override
                    public void delete() {
                        deleteProduct(product);
                    }
                };
                BsItemOptions options = new BsItemOptions();
                options.provider = provider;
                options.show(getChildFragmentManager(),"ITEM_OPTIONS");
            }
        };

        adapter = new ProductListAdapter(requireContext(),listener);
        binding.rv.setAdapter(adapter);
        binding.rv.setLayoutManager(new GridLayoutManager(requireContext(),2, RecyclerView.VERTICAL,false));

        binding.tielSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                loadProducts(s.toString());
            }
        });
        binding.tilSearch.addOnEndIconChangedListener((textInputLayout, previousIcon) -> loadProducts(binding.tielSearch.getText().toString()));

        binding.fab.setOnClickListener(v->{
            BsAddOptions.ActionProvider provider = new BsAddOptions.ActionProvider() {

                @Override
                public void addProduct() {
                    Navigation.findNavController(requireActivity(),R.id.fragContainerView)
                            .navigate(R.id.action_productListFragment_to_addProductFragment);
                }

                @Override
                public void addVendor() {
                    Navigation.findNavController(requireActivity(),R.id.fragContainerView)
                            .navigate(R.id.action_productListFragment_to_addVendorFragment);
                }
            };
            BsAddOptions options = new BsAddOptions();
            options.provider = provider;
            options.show(getChildFragmentManager(),"ADD_OPTIONS");
        });

        loadProducts(lastSearch);
    }

    private void deleteProduct(Product product){
        viewModel.delete(product);
    }
    private void editProduct(Product product){
        Bundle bundle = new Bundle();
        bundle.putInt("id",product.getId());
        Navigation.findNavController(requireActivity(),R.id.fragContainerView)
                .navigate(R.id.action_productListFragment_to_addProductFragment,bundle);
    }

    private void loadProducts(String keyword){
        if(viewModel.getProduct(lastSearch).hasObservers()){
            viewModel.getProduct(lastSearch).removeObservers(this);
        }
        lastSearch = keyword;
        viewModel.getProduct(keyword).observe(requireActivity(), products -> {
            adapter.setList((ArrayList<Product>) products);
        });
    }
}
