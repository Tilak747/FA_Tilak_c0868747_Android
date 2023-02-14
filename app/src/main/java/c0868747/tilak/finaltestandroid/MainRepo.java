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

    LiveData<Product> getProduct(int id){
        return myDao.getProduct(id);
    }

    LiveData<List<Product>> getProduct(String filter){
        return myDao.getAllProducts(filter);
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
