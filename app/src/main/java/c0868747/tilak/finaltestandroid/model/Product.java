package c0868747.tilak.finaltestandroid.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "product",
        indices = {@Index("vendor_name")},
        foreignKeys = @ForeignKey(entity = Vendor.class,
        parentColumns = "name",
        childColumns = "vendor_name",
        onDelete = CASCADE)
)
public class Product {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "vendor_id")
    private int vendorId;

    private String name;
    private String description;
    private double price;


    @ColumnInfo(name = "vendor_name")
    private String vendorName;

    @Ignore
    public Product() {
    }

    public Product(String name, String description, double price, String vendorName) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.vendorName = vendorName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }
}
