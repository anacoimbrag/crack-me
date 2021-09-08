package com.anacoimbra.crackme.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.anacoimbra.crackme.data.Bookmark
import com.anacoimbra.crackme.domain.defaultPref
import com.anacoimbra.crackme.domain.get
import com.anacoimbra.crackme.domain.getDatabase
import com.anacoimbra.crackme.domain.retrofit
import kotlinx.coroutines.launch

class MainViewModel(app: Application) : AndroidViewModel(app), Listener {

    private val dao = getDatabase(getApplication()).bookmarkDao()

    private val _randomFact = MutableLiveData<String>()
    val randomFact: LiveData<String>
        get() = _randomFact

    private val _bookmarked = liveData {
        val bookmarkedLiveData = dao.getAllBookmarked()
        emitSource(bookmarkedLiveData.map { list -> list.map { it.text } })
    }
    val bookmarked: LiveData<List<String>>
        get() = _bookmarked

    override fun generateNewFact() {
        viewModelScope.launch {
            try {
                val type = defaultPref(getApplication())["likes"] ?: "cat"
                val fact = retrofit.getRandomFact(type)
                Log.d("Fact", fact.text.orEmpty())
                _randomFact.postValue(fact.text.orEmpty())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun bookmarkFact(fact: String, checked: Boolean) {
        viewModelScope.launch {
            if (checked) dao.addBookmark(Bookmark(text = fact))
            else dao.removeBookmark(Bookmark(text = fact))
        }
    }
}