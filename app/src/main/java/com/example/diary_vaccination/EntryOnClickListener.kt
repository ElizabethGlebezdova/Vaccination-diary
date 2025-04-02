package com.example.diary_vaccination

interface EntryOnClickListener {
    fun entryOnClick(id: String)

    fun navigateEdit(id: String)
}