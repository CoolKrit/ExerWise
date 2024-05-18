package com.example.exerwise

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.exerwise.data.db.AppDatabase
import com.example.exerwise.data.db.ExerciseDao
import com.example.exerwise.data.model.ExerciseEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExerciseDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var exerciseDao: ExerciseDao

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        exerciseDao = database.exerciseDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testInsertAndGetAllExercises() = runBlocking {
        val exercise = ExerciseEntity(1, "Push Up", "Chest", "None", "Pectorals", "", emptyList())
        exerciseDao.insertAll(listOf(exercise))
        val exercises = exerciseDao.getAllExercises()
        assertEquals(1, exercises.size)
        assertEquals(exercise, exercises[0])
    }

    @Test
    fun testGetExercisesByName() = runBlocking {
        val exercise1 = ExerciseEntity(1, "Push Up", "Chest", "None", "Pectorals", "", emptyList())
        val exercise2 = ExerciseEntity(2, "Pull Up", "Back", "None", "Lats", "", emptyList())
        exerciseDao.insertAll(listOf(exercise1, exercise2))
        val exercises = exerciseDao.getExercisesByName("%Push%")
        assertEquals(1, exercises.size)
        assertEquals(exercise1, exercises[0])
    }

    @Test
    fun testGetExercisesFiltered() = runBlocking {
        val exercise1 = ExerciseEntity(1, "Push Up", "Chest", "None", "Pectorals", "", emptyList())
        val exercise2 = ExerciseEntity(2, "Pull Up", "Back", "None", "Lats", "", emptyList())
        exerciseDao.insertAll(listOf(exercise1, exercise2))
        val exercises = exerciseDao.getExercisesFiltered("%Push%", "%Chest%", "%None%")
        assertEquals(1, exercises.size)
        assertEquals(exercise1, exercises[0])
    }
}