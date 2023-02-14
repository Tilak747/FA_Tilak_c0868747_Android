package c0868747.tilak.finaltestandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import c0868747.tilak.finaltestandroid.databinding.BsAddOptionsBinding;

public class BsAddOptions extends BottomSheetDialogFragment {

    interface ActionProvider{
        void addProduct();
        void addVendor();
    }
    ActionProvider provider;

    BsAddOptionsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BsAddOptionsBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.btnAddProduct.setOnClickListener(v->{
            provider.addProduct();
            dismiss();
        });
        binding.btnAddProduct.setOnClickListener(v->{
            provider.addVendor();
            dismiss();
        });
    }
}
