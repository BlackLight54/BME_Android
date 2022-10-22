package hu.bme.aut.android.todo.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodoDao {

    @Insert
    fun insertTodo(todo: RoomTodo)

    @Query("SELECT * FROM todo")
    fun getAllTodos(): LiveData<List<RoomTodo>>

    @Update
    fun updateTodo(todo: RoomTodo): Int

    @Delete
    fun deleteTodo(todo: RoomTodo)

}