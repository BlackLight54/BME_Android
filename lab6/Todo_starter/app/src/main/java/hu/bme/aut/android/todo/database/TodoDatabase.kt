package hu.bme.aut.android.todo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    version = 1,
    exportSchema = false,
    entities = [RoomTodo::class]
)
@TypeConverters(
    TodoTypeConverter::class
)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao

}