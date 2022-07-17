package com.example.onlineshop.screen.card

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineshop.databinding.FragmentCardBinding
import com.example.onlineshop.model.ProductModel
import com.example.onlineshop.screen.MainViewModel
import com.example.onlineshop.screen.makeorder.MakeOrderActivity
import com.example.onlineshop.utils.Constants
import com.example.onlineshop.utils.PrefUtils
import com.example.onlineshop.view.cartAdapter
import java.io.Serializable

class CardFragment : Fragment() {

    lateinit var viewModel: MainViewModel
    lateinit var binding: FragmentCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.progress.observe(this, Observer {
            binding.swipe.isRefreshing = it
        })

        viewModel.error.observe(this, Observer {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        })

        viewModel.productsData.observe(this, Observer{
            binding.recycler.adapter = it?.let { it1 -> cartAdapter(it1) }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.layoutManager = LinearLayoutManager(requireActivity())


        binding.swipe.setOnRefreshListener {
            loadData()
        }

        binding.btnMakeOrder.setOnClickListener {
            val intent = Intent(requireActivity(), MakeOrderActivity::class.java)
            intent.putExtra(Constants.EXTR_DATA, (viewModel.productsData.value ?: emptyList<ProductModel>()) as Serializable)
            startActivity(intent)
        }

        loadData()

    }

    fun loadData(){
        viewModel.getProductByIds(PrefUtils.getCartList().map { it.product_id })
    }

    companion object {
        @JvmStatic
        fun newInstance() = CardFragment()
    }
}