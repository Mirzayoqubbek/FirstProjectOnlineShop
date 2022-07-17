package com.example.onlineshop.screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onlineshop.api.repository.ShopRepository
import com.example.onlineshop.db.AppDatabase
import com.example.onlineshop.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel: ViewModel() {

    val repository = ShopRepository()

    val error = MutableLiveData<String>()

    val progress = MutableLiveData<Boolean>()

    val offersData = MutableLiveData<List<OfferModel>>()
    val categoriesData = MutableLiveData<List<CategoryModel>>()
    val productsData = MutableLiveData<List<ProductModel>?>()

    fun getOffers(){
        repository.getOffers(error, progress, offersData)
    }

    fun getCategories(){
        repository.getCategories(error, categoriesData)
    }

    fun getTopProducts(){
        repository.getTopProducts(error, productsData)
    }

    fun getProductByCategory(id: Int){
        repository.getProductByCategory(id, error, productsData)
    }

    fun getProductByIds(ids: List<Int>){
        repository.getProductByIds( ids, error, progress, productsData)
    }

    fun insertAllProducts2DB(items: List<ProductModel>){
        CoroutineScope(Dispatchers.IO).launch{
            AppDatabase.getDatabase().getProductDao().insetAll(items)
        }
    }

    fun insertAllCategories2DB(items: List<CategoryModel>){
        CoroutineScope(Dispatchers.IO).launch{
            AppDatabase.getDatabase().getCategoryDao().insetAll(items)
        }
    }

    fun getAllDBProducts(){
        CoroutineScope(Dispatchers.Main).launch{
            productsData.value = withContext(Dispatchers.IO){AppDatabase.getDatabase().getProductDao().getAllProducts()}
        }
    }

    fun getAllDBCategorry(){
        CoroutineScope(Dispatchers.Main).launch{
            categoriesData.value = withContext(Dispatchers.IO){AppDatabase.getDatabase().getCategoryDao().getAllCategories()}
        }
    }
}