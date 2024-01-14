package io.dhruv.starwars.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import io.dhruv.starwars.db.CharacterDB
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideCharacterDB(context :Context) : CharacterDB{
        return Room.databaseBuilder(
            context = context,
            klass = CharacterDB::class.java,
            name = "CharacterDB"
        ).build()
    }
}