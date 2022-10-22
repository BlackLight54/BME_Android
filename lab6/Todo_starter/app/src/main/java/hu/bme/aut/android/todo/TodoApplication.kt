package hu.bme.aut.android.todo

import android.app.Application
import androidx.room.Room
import hu.bme.aut.android.todo.database.TodoDatabase

class TodoApplication : Application() {

    companion object {
        lateinit var todoDatabase: TodoDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()

        todoDatabase = Room.databaseBuilder(
            applicationContext,
            TodoDatabase::class.java,
            "todo_database"
        ).fallbackToDestructiveMigration().build()
    }

}