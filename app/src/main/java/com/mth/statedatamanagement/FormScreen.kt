package com.mth.statedatamanagement

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.mth.statedatamanagement.databinding.FormLayoutBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class FormScreen : AppCompatActivity() {

    private lateinit var mBinding: FormLayoutBinding
    private val defaultButtonTintColor = "#1B1717"
    private val onFormValidButtonTintColor = "#4F774F"
    private var errorMessage: String? = null
    private var alertErrorMessage: String? = null

    private val _firstName = MutableStateFlow("")
    private val _lastName = MutableStateFlow("")
    private val _email = MutableStateFlow("")
    private val _doB = MutableStateFlow("")
    private val _nationality = MutableStateFlow("")
    private val _country = MutableStateFlow("")

    private lateinit var mobilePrefix: TextView


    private val formIsValid = combine(
        _firstName,
        _lastName,
        _email,
        _doB,
        _nationality,
        _country,
    ) {

        val firstNameIsValid = _firstName.value.isNotEmpty()
        val lastNameIsValid = _lastName.value.isNotEmpty()
        val emailIsValid = _email.value.isNotEmpty()
        val doBIsValid = _doB.value.isNotEmpty()
        val nationalityIsValid = _nationality.value.isNotEmpty()
        val countryIsValid = _country.value.isNotEmpty()

        errorMessage = when {
            firstNameIsValid.not() -> "fill complete First Name"
            lastNameIsValid.not() -> "fill complete Last Name"
            emailIsValid.not() -> "email not valid"
            doBIsValid.not() -> "fill complete Date of Birth"
            nationalityIsValid.not() -> "fill complete Nationality"
            countryIsValid.not() -> "fill complete country"
            else -> null
        }

        errorMessage?.let {
            alertErrorMessage = it
        }

        firstNameIsValid and lastNameIsValid and emailIsValid and doBIsValid and nationalityIsValid and countryIsValid

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = FormLayoutBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        with(mBinding) {
            inputFirstName.doOnTextChanged { text, _, _, _ ->
                _firstName.value = text.toString()
            }
            inputLastName.doOnTextChanged { text, _, _, _ ->
                _lastName.value = text.toString()
            }

            inputEmailAddress.doOnTextChanged { text, _, _, _ ->
                _email.value = text.toString()
            }

            inputDob.doOnTextChanged { text, _, _, _ ->
                _doB.value = text.toString()
            }
            inputNationality.doOnTextChanged { text, _, _, _ ->
                _nationality.value = text.toString()
            }
            inputCountryOfResidence.doOnTextChanged { text, _, _, _ ->
                _country.value = text.toString()
            }


        }

        val snackBar = Snackbar.make(mBinding.root, "Login Successfully", Snackbar.LENGTH_LONG)
        mBinding.btnCreateAccount.setOnClickListener {

            if(alertErrorMessage.equals("true")){
                snackBar.show()
            }else{
                Toast.makeText(applicationContext, "Fill Complete Form", Toast.LENGTH_SHORT)
                    .show()

            }
        }

        lifecycleScope.launch {
            formIsValid.collect {
                mBinding.btnCreateAccount.apply {
                    backgroundTintList = ColorStateList.valueOf(
                        Color.parseColor(
                            if (it) {
                                onFormValidButtonTintColor
                            } else {
                                defaultButtonTintColor
                            }
                        )

                    )

                   alertErrorMessage = it.toString()
                }
            }


        }

    }


}


