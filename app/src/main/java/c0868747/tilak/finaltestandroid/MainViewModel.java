package c0868747.tilak.finaltestandroid;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import c0868747.tilak.finaltestandroid.model.Product;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {

    MainRepo repo;

    @Inject
    public MainViewModel(MainRepo repo){
        this.repo = repo;
    }

    LiveData<List<Product>> getProduct(String filter){
        return repo.getProduct(filter);
    }
}
