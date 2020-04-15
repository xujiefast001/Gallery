package com.example.gallery

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class GalleryViewmodel(application: Application) : AndroidViewModel(application) {
    private val _photolistlive= MutableLiveData<List<PhotoItem>>()
    val photolistlive:LiveData<List<PhotoItem>>
    get()=_photolistlive

}