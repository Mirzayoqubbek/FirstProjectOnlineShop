package com.example.onlineshop.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.onlineshop.R
import com.example.onlineshop.databinding.ActivityMainBinding
import com.example.onlineshop.screen.card.CardFragment
import com.example.onlineshop.screen.changelanguange.ChangeLanguangeFragment
import com.example.onlineshop.screen.favorite.FavoriteFragment
import com.example.onlineshop.screen.home.HomeFragment
import com.example.onlineshop.screen.profile.ProfileFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel

    val homeFragment = HomeFragment.newInstance()
    val favoriteFragment = FavoriteFragment.newInstance()
    var cardFragment = CardFragment.newInstance()
    val profileFragment = ProfileFragment.newInstance()
    var activeFragment: Fragment = homeFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = MainViewModel()

        viewModel.productsData.observe(this, Observer {
            if (it != null) {
                viewModel.insertAllProducts2DB(it)
            }
        })

        viewModel.categoriesData.observe(this, Observer {
            if (it != null) {
                viewModel.insertAllCategories2DB(it)
            }
        })

        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        supportFragmentManager.beginTransaction().add(R.id.flContiner, homeFragment, homeFragment.tag).hide(homeFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.flContiner, cardFragment, cardFragment.tag).hide(cardFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.flContiner, favoriteFragment, favoriteFragment.tag).hide(favoriteFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.flContiner, profileFragment, profileFragment.tag).hide(profileFragment).commit()

        supportFragmentManager.beginTransaction().show(activeFragment).commit()

        binding.BottomNavigationView.setOnItemSelectedListener {
            if (it.itemId == R.id.actionHome) {
                supportFragmentManager.beginTransaction().hide(activeFragment).show(homeFragment).commit()
                activeFragment = homeFragment
            } else if (it.itemId == R.id.actionCard){
                supportFragmentManager.beginTransaction().hide(activeFragment).show(cardFragment).commit()
                activeFragment = cardFragment
            } else if (it.itemId == R.id.actionFavorites){
                supportFragmentManager.beginTransaction().hide(activeFragment).show(favoriteFragment).commit()
                activeFragment = favoriteFragment
            } else if (it.itemId == R.id.actionProfile){
                supportFragmentManager.beginTransaction().hide(activeFragment).show(profileFragment).commit()
                activeFragment = profileFragment
            }

            return@setOnItemSelectedListener true
        }

        binding.btnMenu.setOnClickListener {
            val fragment = ChangeLanguangeFragment.newInstance()
            fragment.show(supportFragmentManager, fragment.tag)
        }

        loadData()
    }

    fun loadData(){
        viewModel.getTopProducts()
        viewModel.getCategories()
    }
}