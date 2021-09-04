package com.example.airlinesapp.ui.addairline


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.airlinesapp.R
import com.example.airlinesapp.data.pojo.AddAirlineRequest
import com.example.airlinesapp.databinding.BottomSheetAddAirlineBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout

class AddAirlineBottomDialogFragment: BottomSheetDialogFragment() , View.OnClickListener {

    private lateinit var bindig:BottomSheetAddAirlineBinding

    companion object{
        const val TAG = "AddAirlineBottomDialog"
        fun newInstance(): AddAirlineBottomDialogFragment {
            return AddAirlineBottomDialogFragment()
        }
    }

    var setOnButtonClickListener: ButtonClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindig = BottomSheetAddAirlineBinding.inflate(layoutInflater,container,false)
        return bindig.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindig.apply {
            confirmBtn.setOnClickListener(this@AddAirlineBottomDialogFragment)
        }
    }

    override fun onClick(v: View?) {
        if (setOnButtonClickListener!=null) {
            val airline = getDataFromTextFields()
            if (airline.name.isNotEmpty()) {
                setOnButtonClickListener!!.onClick(addAirline = airline)
                dismiss()
            }
        }
    }

    private fun getDataFromTextFields(): AddAirlineRequest {
        val airline = AddAirlineRequest()
        bindig.apply {
            if (validateInputs(nameTf)){
                airline.name = nameTf.editText!!.text.toString()
                airline.country = countryTf.editText!!.text.toString()
                airline.slogan = sloganTf.editText!!.text.toString()
                airline.head_quaters = headquarterTf.editText!!.text.toString()
            }
        }
        return airline
    }

    private fun validateInputs(inputLayout: TextInputLayout):Boolean{
        var content = inputLayout.editText!!.text.toString()
        if (content.isEmpty() && content.isBlank()){
            inputLayout.isErrorEnabled = true
            inputLayout.error =getString(R.string.input_required)
            return false
        }else{
            inputLayout.isErrorEnabled = false
        }
        return true
    }

    interface ButtonClickListener {
        fun onClick(addAirline: AddAirlineRequest)
    }
}