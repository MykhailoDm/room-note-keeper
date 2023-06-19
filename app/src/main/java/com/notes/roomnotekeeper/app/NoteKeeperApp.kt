package com.notes.roomnotekeeper.app

import android.app.Application
import com.notes.roomnotekeeper.db.NoteDatabase

class NoteKeeperApp: Application() {
    val db by lazy {
        NoteDatabase.getInstance(this)
    }
}