package c0868747.tilak.finaltestandroid.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import c0868747.tilak.finaltestandroid.model.Product;
import c0868747.tilak.finaltestandroid.model.Vendor;

@Dao
public abstract class MyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(Product product);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(Vendor vendor);

    @Update
    public abstract void update(Vendor vendor);
    @Update
    public abstract void update(Product product);

    @Delete
    public abstract void delete(Vendor vendor);
    @Delete
    public abstract void delete(Product product);

    @Query("Select * from product where id == :id")
    public abstract LiveData<Product> getProduct(int id);

    @Query("Select * from product where name LIKE '%' || :filter || '%' OR description LIKE '%' || :filter || '%'")
    public abstract LiveData<List<Product>> getAllProducts(String filter);

    @Query("SELECT * FROM vendors")
    public abstract LiveData<List<Vendor>> getAllVendors();

}
