package c0868747.tilak.finaltestandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import c0868747.tilak.finaltestandroid.databinding.BsItemOptionsBinding;

public class BsItemOptions extends BottomSheetDialogFragment {

    BsItemOptionsBinding binding;

    interface ActionProvider{
        void edit();
        void delete();
    }
    ActionProvider provider;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BsItemOptionsBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.btnEdit.setOnClickListener(v ->{
            provider.edit();
            dismiss();
        });
        binding.btnDelete.setOnClickListener(v ->{
            provider.delete();
            dismiss();
        });
    }
}
