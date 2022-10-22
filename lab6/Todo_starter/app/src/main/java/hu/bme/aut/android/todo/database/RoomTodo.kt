package hu.bme.aut.android.todo.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import hu.bme.aut.android.todo.model.Todo

@Entity(tableName = "todo")
data class RoomTodo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val priority: Todo.Priority,
    val dueDate: String,
    val description: String
)