
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import ru.itis.kpfu.diyor.Constants.APP_PREFERENCES
import ru.itis.kpfu.diyor.Constants.PREF_EMAIL
import ru.itis.kpfu.diyor.Constants.PREF_PASSWORD
import ru.itis.kpfu.diyor.R
import ru.itis.kpfu.diyor.databinding.FragmentSignupBinding

class SignUpFragment: Fragment(R.layout.fragment_signup) {
    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!
    private lateinit var preferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSignupBinding.bind(view)
        preferences = requireActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

        with(binding) {
            btCreate.setOnClickListener {
                checkPassword(tiSignUpPassword,tiSignUpConfirmPassword)
                checkEmail(tiSignUpEmail)
                checkField(tiSignUpEmail)
                checkField(tiSignUpPassword)
                checkField(tiSignUpConfirmPassword)
                if (checkEmail(tiSignUpEmail) &&
                    checkField(tiSignUpEmail) &&
                    checkField(tiSignUpPassword) &&
                    checkField(tiSignUpConfirmPassword) &&
                    checkPassword(tiSignUpPassword,tiSignUpConfirmPassword)) {
                    findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
                    preferences.edit {
                        putString(PREF_EMAIL, tiSignUpEmail.editText?.text.toString())
                        putString(PREF_PASSWORD, tiSignUpPassword.editText?.text.toString())
                        commit()
                    }
                }
            }
        }
    }

    private fun checkField(view: TextInputLayout): Boolean {
        return if (view.editText?.text.toString().isEmpty()) {
            view.error = getString(R.string.error_signUp)
            false
        } else {
            view.error = null
            true
        }
    }

    private fun checkEmail(view: TextInputLayout): Boolean {
        return if (isEmailValid(view)){
            view.error = getString(R.string.error_email)
            false
        } else {
            view.error = null
            true
        }
    }

    private fun checkPassword(view_1: TextInputLayout, view_2: TextInputLayout): Boolean {
        return if (isPasswordValid(view_1)) {
            view_1.error = getString(R.string.error_password)
            false
        } else if(view_1.editText?.text.toString() != view_2.editText?.text.toString()) {
            view_1.error = getString(R.string.error_signUp_password)
            view_2.error = getString(R.string.error_signUp_password)
            false
        } else {
            view_1.error = null
            view_2.error = null
            true
        }
    }

    private fun isEmailValid(view: TextInputLayout): Boolean {
        val regex = "^[A-Za-z0-9][A-Za-z0-9\\.\\-_]*[A-Za-z0-9]*@([A-Za-z0-9]+([A-Za-z0-9\\-]*[A-Za-z0-9]+)*\\.)+[A-Za-z]*$"
        return !Regex(regex).matches(view.editText?.text.toString())
    }

    private fun isPasswordValid(view: TextInputLayout): Boolean {
        val regex = "(?=.*[0-9])(?=.*[A-Z])[A-Za-z0-9]{6,30}"
        return !Regex(regex).matches(view.editText?.text.toString())
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}

