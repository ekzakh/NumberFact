package com.ekzak.numberfact

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ekzak.numberfact.data.cache.NumberCache
import com.ekzak.numberfact.data.cache.NumbersDao
import com.ekzak.numberfact.data.cache.NumbersDataBase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RoomTest {

    private lateinit var db: NumbersDataBase
    private lateinit var dao: NumbersDao

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, NumbersDataBase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.numbersDao()
    }

    @After
    @Throws(IOException::class)
    fun clear() {
        db.close()
    }

    @Test
    fun test_add_and_check_added() = runBlocking {
        val number = NumberCache("33", "fact", 100)
        assertEquals(null, dao.numberFact("33"))
        dao.insert(number)
        val actual = dao.numberFact("33")
        assertEquals(number, actual)
    }

    @Test
    fun test_update() = runBlocking {
        val number = NumberCache("33", "fact", 100)
        dao.insert(number)
        val new = NumberCache("33", "fact", 300)
        dao.insert(new)
        val allNumbers = dao.allNumbers()

        assertEquals(1, allNumbers.size)
        assertEquals(new, allNumbers[0])
    }
}
