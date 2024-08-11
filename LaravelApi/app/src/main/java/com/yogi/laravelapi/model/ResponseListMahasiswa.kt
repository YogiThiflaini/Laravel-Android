package com.yogi.laravelapi.model

import com.google.gson.annotations.SerializedName

data class ResponseListMahasiswa(

	@field:SerializedName("data")
	val data: List<DataMahasiswa?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataMahasiswa(

	@field:SerializedName("usia")
	val usia: String? = null,

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("nim")
	val nim: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("agama")
	val agama: String? = null,

	@field:SerializedName("idmahasiswa")
	val idmahasiswa: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("namamahasiswa")
	val namamahasiswa: String? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null
)
