package com.yogi.laravelapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yogi.laravelapi.databinding.ListMahasiswaAdapterBinding
import com.yogi.laravelapi.model.DataMahasiswa

class MahasiswaAdapter (
    private val listMahasiswa: List<DataMahasiswa?>?,
): RecyclerView.Adapter<MahasiswaAdapter.ViewHolder>(){
    inner class ViewHolder(val ListMahasiswaAdapterBinding: ListMahasiswaAdapterBinding):
        RecyclerView.ViewHolder(ListMahasiswaAdapterBinding.root){
        fun  onBindItem(dataMahasiswa: DataMahasiswa?){
            ListMahasiswaAdapterBinding.namamahasiswa.text = dataMahasiswa?.namamahasiswa
            ListMahasiswaAdapterBinding.nim.text = dataMahasiswa?.nim
            ListMahasiswaAdapterBinding.alamat.text = dataMahasiswa?.alamat
            ListMahasiswaAdapterBinding.jeniskelamin.text = dataMahasiswa?.gender
            ListMahasiswaAdapterBinding.agama.text = dataMahasiswa?.agama
            ListMahasiswaAdapterBinding.usia.text = dataMahasiswa?.usia
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MahasiswaAdapter.ViewHolder {
        val binding =
            ListMahasiswaAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MahasiswaAdapter.ViewHolder, position: Int) {
        holder.onBindItem(listMahasiswa?.get(position))
    }

    override fun getItemCount(): Int {
        return listMahasiswa?.size?:0
    }

}