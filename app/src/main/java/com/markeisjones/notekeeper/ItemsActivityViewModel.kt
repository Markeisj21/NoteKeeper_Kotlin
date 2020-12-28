package com.markeisjones.notekeeper

import android.arch.lifecycle.ViewModel
import android.os.Bundle

class ItemsActivityViewModel : ViewModel() {
    var isNewlyCreated = true
    val navDrawerDisplaySelectionName = "com.markiesjones.notekeeper.ItemsActivityViewModel.navDrawerDisplaySelection"
    var navDrawerDisplaySelction = R.id.nav_notes

    var recentlyViewedNoteIdsName = "com.markeisjones.notekeeper.ItemsActivityVIewModel.recentlyViewedNoteIds"

    private val maxRecentlyViewedNotes = 5
    val recentlyViewedNotes = ArrayList<NoteInfo>(maxRecentlyViewedNotes)

    fun addToRecentlyViewedNotes(note: NoteInfo) {
        // Check if selection is already in the list
        val existingIndex = recentlyViewedNotes.indexOf(note)
        if (existingIndex == -1) {
            // it isn't in the list...
            // Add new one to beginning of list and remove any beyond max we want to keep
            recentlyViewedNotes.add(0, note)
            for (index in recentlyViewedNotes.lastIndex downTo maxRecentlyViewedNotes)
                recentlyViewedNotes.removeAt(index)
        } else {
            // it is in the list...
            // Shift the ones above down the list and make it first member of the list
            for (index in (existingIndex - 1) downTo 0)
                recentlyViewedNotes[index + 1] = recentlyViewedNotes[index]
            recentlyViewedNotes[0] = note
        }
    }

    fun saveState(outState: Bundle) {
        outState.putInt(navDrawerDisplaySelectionName,navDrawerDisplaySelction)
        val noteIds = DataManager.noteIdsAsIntArray(recentlyViewedNotes)
        outState.putIntArray(recentlyViewedNoteIdsName, noteIds)
    }

    fun restoreState(savedInstanceState: Bundle) {
        navDrawerDisplaySelction = savedInstanceState.getInt(navDrawerDisplaySelectionName)
        val noteIds= savedInstanceState.getIntArray(recentlyViewedNoteIdsName)
        val noteList = DataManager.loadNotes(*noteIds!!)
        recentlyViewedNotes.addAll(noteList)
    }

}