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

    void addProduct(Product product){
        repo.addProduct(product);
    }
    void updateProduct(Product product){
        repo.updateProduct(product);
    }

    LiveData<Product> getProduct(long id){
        return repo.getProduct(id);
    }

    LiveData<List<Product>> getProduct(String filter){
        return repo.getProduct(filter);
    }

    LiveData<List<Vendor>> getVendors(){
        return repo.getVendors();
    }
    Vendor getVendor(long id){
        return repo.getVendor(id);
    }
    LiveData<Vendor> getVendor(String name){
        return repo.getVendor(name);
    }

    void delete(Product product){
        repo.delete(product);
    }

    public long addVendor(Vendor vendor){
        return repo.addVendor(vendor);
    }
}
