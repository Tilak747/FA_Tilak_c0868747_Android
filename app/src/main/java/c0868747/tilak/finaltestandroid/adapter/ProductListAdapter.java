package c0868747.tilak.finaltestandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import c0868747.tilak.finaltestandroid.databinding.ItemProductListBinding;
import c0868747.tilak.finaltestandroid.model.Product;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.VH>{

    final private Context context;
    ArrayList<Product> list = new ArrayList<>();
    ProductItemListener listener;

    public ProductListAdapter(Context context,ProductItemListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public interface ProductItemListener{
        void viewProduct(Product product);
        void showMenu(Product product);
    }

    public void setList(ArrayList<Product> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductListBinding binding = ItemProductListBinding.inflate(
                LayoutInflater.from(context),parent,false
        );
        return new VH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.bind(list.get(position),listener,position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class VH extends RecyclerView.ViewHolder{

        ItemProductListBinding binding;

        public VH(ItemProductListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(Product product,ProductItemListener listener,int position){
            binding.container.setOnClickListener(v->{
                listener.viewProduct(product);
            });
            binding.ivInfo.setOnClickListener(v->{
                listener.showMenu(product);
            });
            binding.tvTitle.setText(product.getName());
            binding.tvDesc.setText(product.getDescription());
            binding.chipVendor.setText(product.getVendorName());
        }
    }

}
