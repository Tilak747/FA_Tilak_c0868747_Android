package c0868747.tilak.finaltestandroid;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import c0868747.tilak.finaltestandroid.model.Product;
import c0868747.tilak.finaltestandroid.model.Vendor;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {

    MainRepo repo;

    @Inject
    public MainViewModel(MainRepo repo){
        this.repo = repo;
    }

    LiveData<Product> getProduct(int id){
        return repo.getProduct(id);
    }

    LiveData<List<Product>> getProduct(String filter){
        return repo.getProduct(filter);
    }

    void delete(Product product){
        repo.delete(product);
    }

    public long addVendor(Vendor vendor){
        return repo.addVendor(vendor);
    }
}
