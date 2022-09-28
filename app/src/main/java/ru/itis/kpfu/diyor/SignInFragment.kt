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
import ru.itis.kpfu.diyor.Constants.PREF_LOGIN_STATUS
import ru.itis.kpfu.diyor.Constants.PREF_PASSWORD
import ru.itis.kpfu.diyor.R
import ru.itis.kpfu.diyor.databinding.FragmentSigninBinding

class SignInFragment: Fragment(R.layout.fragment_signin) {
    private var _binding: FragmentSigninBinding? = null
    private val binding get() = _binding!!
    private lateinit var preferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSigninBinding.bind(view)

        preferences = requireActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

        with(binding){
            btSignIn.setOnClickListener {
                checkField(tiSignInEmail)
                checkField(tiSignInPassword)
                if (checkField(tiSignInEmail) && checkField(tiSignInPassword) &&
                    checkCorrectEmail(tiSignInEmail) &&
                    checkCorrectPassword(tiSignInPassword)
                    ) {
                    findNavController().navigate(R.id.action_signInFragment_to_profileFragment)
                    preferences.edit {
                        putBoolean(PREF_LOGIN_STATUS, true)
                        commit()
                    }
                }
            }
            btSignUp.setOnClickListener {
                findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
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

    private fun checkCorrectEmail(view: TextInputLayout): Boolean {
        val currentUsername = preferences.getString(PREF_EMAIL, "error").orEmpty()
        return if (view.editText?.text.toString() != currentUsername){
            view.error = getString(R.string.error_signIn_email)
            false
        } else {
            view.error = null
            true
        }
    }

    private fun checkCorrectPassword(view: TextInputLayout): Boolean {
        val currentPassword = preferences.getString(PREF_PASSWORD, "error").orEmpty()
        return if(view.editText?.text.toString() != currentPassword) {
            view.error = getString(R.string.error_signIn_password)
            false
        } else {
            view.error = null
            true
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}