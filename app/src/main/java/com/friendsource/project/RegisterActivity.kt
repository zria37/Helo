package com.friendsource.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.friendsource.project.databinding.ActivityLoginBinding
import com.friendsource.project.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
//    Deklarasikan instance FirebaseAuth.
    private lateinit var auth: FirebaseAuth
//    End Deklarasikan instance FirebaseAuth.

    override fun onCreate(savedInstanceState: Bundle?) {

        lateinit var binding: ActivityRegisterBinding
//        Dalam metode onCreate(), lakukan inisialisasi instance FirebaseAuth.
        auth = Firebase.auth
//       End Dalam metode onCreate(), lakukan inisialisasi instance FirebaseAuth.

        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegistrasi.setOnClickListener {
            val email           = binding.etEmail.text.toString().trim()
            val name            = binding.etName.text.toString().trim()
            val phone           = binding.etPhone.text.toString().trim()
            val password        = binding.etPassword.text.toString().trim()
            val confirmpassword = binding.etConfirmPassword.text.toString().trim()

                if (email.isEmpty()){
                    binding.etEmail.error = "Email Required"
                    binding.etEmail.requestFocus()
                    return@setOnClickListener
                }
                if (password.isEmpty() || password.length < 6){
                    binding.etPassword.error = "Password Required"
                    binding.etPassword.requestFocus()
                    return@setOnClickListener
                }
                if (confirmpassword.isEmpty()){
                    binding.etConfirmPassword.error = "Confirm Password Required"
                    binding.etConfirmPassword.requestFocus()
                    return@setOnClickListener
                }

                if (password != confirmpassword){
                    binding.etConfirmPassword.error = "Confirm Password Not Match"
                    binding.etConfirmPassword.requestFocus()
                    return@setOnClickListener
                }
            registrasiUser(email, password)
        }

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }
    // Saat melakukan inisialisasi Aktivitas Anda, periksa apakah pengguna sudah login atau belum.
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload();
        }
    }
    private fun reload() {
//Mendeklarasi
    }
    // end Saat melakukan inisialisasi Aktivitas Anda, periksa apakah pengguna sudah login atau belum.
    private fun registrasiUser(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this){
            if (it.isSuccessful){
                Toast.makeText(this, "Registrasion Successfull", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                startActivity(intent)
            }else{
                val message = it.exception!!.toString()
                Toast.makeText(this, "Error : $message", Toast.LENGTH_SHORT).show()
            }
        }

    }


}