package c0868747.tilak.finaltestandroid;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import c0868747.tilak.finaltestandroid.db.MyDao;
import c0868747.tilak.finaltestandroid.model.Product;
import c0868747.tilak.finaltestandroid.model.Vendor;

public class MainRepo {

    MyDao myDao;
    ExecutorService executorService;

    @Inject
    public MainRepo(MyDao myDao,ExecutorService executorService){
        this.myDao = myDao;
        this.executorService = executorService;
    }

    void addProduct(Product product){
        executorService.execute( () -> {
            myDao.insert(product);
        });
    }
    void updateProduct(Product product){
        executorService.execute( () -> {
            myDao.update(product);
        });
    }

    LiveData<Product> getProduct(long id){
        return myDao.getProduct(id);
    }

    LiveData<List<Product>> getProduct(String filter){
        return myDao.getAllProducts(filter);
    }

    LiveData<List<Vendor>> getVendors(){
        return myDao.getAllVendors();
    }

    Vendor getVendor(long id){
        return myDao.getVendor(id);
    }
    LiveData<Vendor> getVendor(String name){
        return myDao.getVendor(name);
    }

    void delete(Product product){
        executorService.execute( () -> {
            myDao.delete(product);
        });
    }

    public long addVendor(Vendor vendor){
        executorService.execute( () -> {
            myDao.insert(vendor);
        });
        return vendor.getId();
    }

}
