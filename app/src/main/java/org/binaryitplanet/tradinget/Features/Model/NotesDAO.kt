package org.binaryitplanet.tradinget.Features.Model

import androidx.room.*
import org.binaryitplanet.tradinget.Utils.NotesUtils

@Dao
interface NotesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: NotesUtils): Long

    @Delete
    fun delete(note: NotesUtils): Int

    @Update
    fun update(note: NotesUtils): Int

    @Query("SELECT * FROM Notes_table")
    fun fetchNotesList(): List<NotesUtils>
}