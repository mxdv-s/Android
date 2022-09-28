package ru.itis.kpfu.diyor

import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.itis.kpfu.diyor.Constants.PREF_EMAIL
import ru.itis.kpfu.diyor.Constants.PREF_LAST_NAME
import ru.itis.kpfu.diyor.Constants.PREF_NAME
import ru.itis.kpfu.diyor.databinding.FragmentProfileBinding
import ru.itis.kpfu.diyor.databinding.ProfileNameBinding

class ProfileFragment: Fragment(R.layout.fragment_profile) {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var preferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        preferences = requireActivity().getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE)

        val name = preferences.getString(PREF_NAME, "error").orEmpty()
        val lastName = preferences.getString(PREF_LAST_NAME, "error").orEmpty()
        val email = preferences.getString(PREF_EMAIL, "error").orEmpty()
        binding.tvName.text = "$name $lastName"
        binding.tvEmail.text = email

        binding.btEdit.setOnClickListener {
            showDialog(binding)
        }

        binding.btLogout.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_signInFragment)
            preferences.edit {
                putBoolean(Constants.PREF_LOGIN_STATUS, false)
                commit()
            }
        }

    }

    private fun showDialog(binding: FragmentProfileBinding) {
        val dialogBinding = ProfileNameBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Change name")
            .setView(dialogBinding.root)
            .setPositiveButton("Ok", null)
            .setNegativeButton("Cancel", null)
            .create()

        dialog.setOnShowListener {
            dialogBinding.etChangeName.requestFocus()
            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
                val name = dialogBinding.etChangeName.text.toString()
                val lastName = dialogBinding.etChangeLastname.text.toString()
                if(name.isNotBlank() && lastName.isNotBlank()) {
                    binding.tvName.text = "$name $lastName"
                    preferences.edit {
                        putString(PREF_NAME, name)
                        putString(PREF_LAST_NAME, lastName)
                        commit()
                    }
                    dialog.dismiss()
                } else {
                    return@setOnClickListener
                }
            }
        }
        dialog.show()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}