package com.example.android.hilt.di

import android.content.Context
import androidx.room.Room
import com.example.android.hilt.data.AppDatabase
import com.example.android.hilt.data.LogDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext appContext: Context
    ) : AppDatabase {
        return synchronized(appContext){
            Room.databaseBuilder(
                appContext,
                AppDatabase::class.java,
                "logging.db"
            ).build()
        }
    }

    @Provides
    fun provideLogDao(dataBase: AppDatabase): LogDao{
        return dataBase.logDao()
    }

}