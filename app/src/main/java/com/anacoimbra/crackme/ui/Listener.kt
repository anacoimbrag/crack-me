package com.anacoimbra.crackme.ui

interface Listener {
    fun generateNewFact()
    fun bookmarkFact(fact: String, checked: Boolean)
}