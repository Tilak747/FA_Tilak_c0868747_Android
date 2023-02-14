package c0868747.tilak.finaltestandroid.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import c0868747.tilak.finaltestandroid.model.Product;
import c0868747.tilak.finaltestandroid.model.Vendor;

@Database(entities = {Vendor.class,Product.class},version = 1,exportSchema = false)
public abstract class MyDB extends RoomDatabase {

    public abstract MyDao myDao();

}