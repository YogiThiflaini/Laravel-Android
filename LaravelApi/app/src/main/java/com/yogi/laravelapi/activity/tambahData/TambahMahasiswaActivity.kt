package com.yogi.laravelapi.activity.tambahData

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.yogi.laravelapi.R
import com.yogi.laravelapi.config.NetworkConfig
import com.yogi.laravelapi.databinding.ActivityTambahMahasiswaBinding
import com.yogi.laravelapi.model.SubmitMahasiswa
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TambahMahasiswaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTambahMahasiswaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTambahMahasiswaBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val items = listOf("Islam", "Kristen", "Katolik", "Hindu", "Budha", "Konghuchu")
        val adapter = ArrayAdapter(this, R.layout.list_agama, items)
        binding.dropdownField.setAdapter(adapter)

        binding.saveButton.setOnClickListener{
            saveData()
        }
    }

    private fun saveData() {
        val agama = binding.dropdownField.text.toString()
        val rbLaki = binding.rbLaki
        val rbPerempuan = binding.rbPerempuan

        val gender = if (rbLaki.isChecked){
            rbLaki.text.toString()
        }else{
            rbPerempuan.text.toString()
        }

        val namalengkap = binding.editTextName.text.toString()
        val nim = binding.editTextNim.text.toString()
        val alamat = binding.editTextAlamat.text.toString()
        val usia = binding.editTextUsia.text.toString()

        val retrofit = NetworkConfig().getService()

        if (namalengkap.isNotEmpty() || nim.isNotEmpty() || alamat.isNotEmpty() || gender.isNotEmpty() || agama.isNotEmpty() || usia.isNotEmpty()) {
            retrofit.postMahasiswa(namalengkap, nim, alamat, gender, agama, usia)
                .enqueue(object: Callback<SubmitMahasiswa>{
                    override fun onResponse(
                        call: Call<SubmitMahasiswa>,
                        response: Response<SubmitMahasiswa>
                    ) {
                        if (response.isSuccessful){
                            val hasil = response.body()
                            Toast.makeText(applicationContext, hasil!!.message, Toast.LENGTH_SHORT).show()
                            namalengkap.isEmpty()
                            nim.isEmpty()
                            alamat.isEmpty()
                            gender.isEmpty()
                            agama.isEmpty()
                            usia.isEmpty()
                        }
                    }

                    override fun onFailure(call: Call<SubmitMahasiswa>, t: Throwable) {
                        Toast.makeText(applicationContext, "Data gagal disimpan: ${t.message}",
                            Toast.LENGTH_SHORT).show()
                    }
                })
        } else {
            Toast.makeText(applicationContext, "Field tidak boleh Kosong",
                Toast.LENGTH_SHORT).show()
        }
    }
}