package com.yogi.laravelapi.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.yogi.laravelapi.R
import com.yogi.laravelapi.activity.tambahData.TambahMahasiswaActivity
import com.yogi.laravelapi.adapter.MahasiswaAdapter
import com.yogi.laravelapi.config.NetworkConfig
import com.yogi.laravelapi.databinding.ActivityMainBinding
import com.yogi.laravelapi.model.DataMahasiswa
import com.yogi.laravelapi.model.ResponseListMahasiswa
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)

        val btnTambah = binding.btnTambah
        btnTambah.setOnClickListener{
            val tambah = Intent(this, TambahMahasiswaActivity::class.java)
            startActivity(tambah)
        }

        val swipeRefresh = binding.swipeRefreshLayout
        swipeRefresh.setOnRefreshListener(this)

        val  appbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar_search)
        setSupportActionBar(appbar)
        getMahasiswa()
    }

    private fun getMahasiswa() {
        NetworkConfig().getService()
            .getMahasiswa()
            .enqueue(object: Callback<ResponseListMahasiswa> {
                override fun onResponse(
                    call: Call<ResponseListMahasiswa>,
                    response: Response<ResponseListMahasiswa>
                ) {
                    this@MainActivity.binding.progressIndicator.visibility = View.GONE
                    if (response.isSuccessful){
                        val receiveDatas = response.body()?.data
                        setToAdapter(receiveDatas)
                    }

                    binding.swipeRefreshLayout.isRefreshing = false
                }

                override fun onFailure(call: Call<ResponseListMahasiswa>, t: Throwable) {
                    this@MainActivity.binding.progressIndicator.visibility = View.GONE
                    Log.d("Retrofit onFailure: ", "onFailure: ${t.stackTrace}")

                    binding.swipeRefreshLayout.isRefreshing = false
                }

            })
    }

    private fun cariMahasiswa(query: String?){
        this.binding.progressIndicator.visibility = View.GONE
        NetworkConfig().getService()
            .cariMahasiswa(query)
            .enqueue(object : Callback<ResponseListMahasiswa>{
                override fun onResponse(
                    call: Call<ResponseListMahasiswa>,
                    response: Response<ResponseListMahasiswa>
                ) {
                    this@MainActivity.binding.progressIndicator.visibility = View.GONE
                    if (response.isSuccessful){
                        val receiveDatas = response.body()?.data
                        setToAdapter(receiveDatas)
                    }

                }

                override fun onFailure(call: Call<ResponseListMahasiswa>, t: Throwable) {
                    this@MainActivity.binding.progressIndicator.visibility = View.GONE
                    Log.d("Retrofit onFailure: ", "onFailure: ${t.stackTrace}")
                }

            })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val cariItem = menu?.findItem(R.id.app_bar_search)
        val cariView: SearchView = cariItem?.actionView as SearchView
        cariView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                cariMahasiswa(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        return true
    }

    private fun setToAdapter(receiveDatas: List<DataMahasiswa?>?) {
        val adapter = MahasiswaAdapter(receiveDatas)
        val ln = LinearLayoutManager(this)
        this.binding.rvMahasiswa.layoutManager = ln
        this.binding.rvMahasiswa.itemAnimator = DefaultItemAnimator()
        this.binding.rvMahasiswa.adapter = adapter
    }

    override fun onRefresh() {
        getMahasiswa()
    }
}