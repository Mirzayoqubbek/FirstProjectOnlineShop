package com.example.onlineshop.screen.changelanguange

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.onlineshop.R
import com.example.onlineshop.databinding.FragmentChangeLanguangeBinding
import com.example.onlineshop.databinding.FragmentHomeBinding
import com.example.onlineshop.screen.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.orhanobut.hawk.Hawk

class ChangeLanguangeFragment : BottomSheetDialogFragment() {

    lateinit var binding: FragmentChangeLanguangeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChangeLanguangeBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ChangeLanguangeFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnUzb.setOnClickListener {
            Hawk.put("pref_lang", "uz")
            requireActivity().finish()
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        }
        binding.btnEng.setOnClickListener {
            Hawk.put("pref_lang", "en")
            requireActivity().finish()
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        }
    }
}