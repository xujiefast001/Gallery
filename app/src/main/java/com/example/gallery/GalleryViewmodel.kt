package com.example.gallery

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson

class GalleryViewmodel(application: Application) : AndroidViewModel(application) {
    private val _photolistlive= MutableLiveData<List<PhotoItem>>()
    val photolistlive:LiveData<List<PhotoItem>>
    get()=_photolistlive

    fun fetchData(){
        val stringRequest:StringRequest= StringRequest(
            Request.Method.GET,
            getURL(),
            Response.Listener {
                _photolistlive.value=Gson().fromJson(it,Pixabay::class.java).hits.toList()
            },
            Response.ErrorListener {
                Log.e("hello",it.toString())
            }
        )
        VolleySingleton.getinstance(getApplication()).requestQueue.add(stringRequest)
    }

    private fun getURL():String{
        return "https://pixabay.com/api/?key=16040936-bce3ddfa20ff6f72afb5dff7b&q=${keyWords.random()}&per_page=100"
    }
    private val keyWords= arrayOf("cat","bus","movie","beauty","phone","computer","bird")

}