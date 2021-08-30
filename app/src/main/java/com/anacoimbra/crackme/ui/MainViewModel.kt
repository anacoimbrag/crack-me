package com.anacoimbra.crackme.ui

import android.app.Application
import androidx.lifecycle.*
import com.anacoimbra.crackme.data.Bookmark
import com.anacoimbra.crackme.domain.getDatabase
import com.anacoimbra.crackme.domain.retrofit
import kotlinx.coroutines.Dispatchers
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
                val fact = retrofit.getRandomFact()
                _randomFact.postValue(fact.text.orEmpty())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun bookmarkFact(fact: String, checked: Boolean) {
        viewModelScope.launch(context = Dispatchers.IO) {
            if (checked) dao.addBookmark(Bookmark(text = fact))
            else dao.removeBookmark(Bookmark(text = fact))
        }
    }
}