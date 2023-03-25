package com.ekzak.numberfact.data.cache

import android.content.Context
import androidx.room.Room

interface CacheModule {

    fun provideDataBase(): NumbersDataBase

    class Base(private val context: Context) : CacheModule {

        private val dataBase by lazy {
            return@lazy Room.databaseBuilder(
                context.applicationContext,
                NumbersDataBase::class.java,
                "numbers_database"
            )
                .fallbackToDestructiveMigration()
                .build()
        }

        override fun provideDataBase(): NumbersDataBase {
            return dataBase
        }
    }

    class Mock(private val context: Context) : CacheModule {

        private val dataBase by lazy {
            Room.inMemoryDatabaseBuilder(context, NumbersDataBase::class.java)
                .build()
        }

        override fun provideDataBase(): NumbersDataBase = dataBase
    }
}
