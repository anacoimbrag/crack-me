package com.anacoimbra.crackme.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class MainViewModel : ViewModel(), Listener {

    private val _randomFact = MutableLiveData<String>()
    val randomFact: LiveData<String>
        get() = _randomFact

    private val _bookmarked = MutableLiveData<List<String>>()
    val bookmarked: LiveData<List<String>>
        get() = _bookmarked

    override fun generateNewFact() {
        _randomFact.postValue("New random string ${Random.nextInt()}")
    }

    override fun bookmarkFact(fact: String, checked: Boolean) {
        if (checked)
            _bookmarked.postValue(_bookmarked.value.orEmpty().plus(fact))
        else
            _bookmarked.postValue(_bookmarked.value?.minus(fact))
    }
}