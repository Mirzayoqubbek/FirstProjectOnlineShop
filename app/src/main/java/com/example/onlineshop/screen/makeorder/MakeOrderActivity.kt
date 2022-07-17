package com.example.onlineshop.screen.makeorder

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.onlineshop.MapsActivity
import com.example.onlineshop.databinding.ActivityMaceOrderBinding
import com.example.onlineshop.model.AddressModel
import com.example.onlineshop.model.ProductModel
import com.example.onlineshop.utils.Constants
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class MakeOrderActivity : AppCompatActivity() {

    lateinit var binding: ActivityMaceOrderBinding

    var address: AddressModel? = null
    lateinit var items: List<ProductModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMaceOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        items = intent.getSerializableExtra(Constants.EXTR_DATA)  as List<ProductModel>

        binding.Back.setOnClickListener {
            finish()
        }

        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this )
        }

        binding.TvTotolAmout.setText(items.sumByDouble { it.cart_count.toDouble() * (it.price.replace(" ", "").toDoubleOrNull() ?: 0.0) }.toString())

        binding.editAddres.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this)
        }
    }

    @Subscribe
    fun onEvent(address: AddressModel){
        this.address = address
        binding.editAddres.setText("${address.latitude}, ${address.longitude}")
    }
}