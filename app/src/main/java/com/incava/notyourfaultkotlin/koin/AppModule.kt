package com.incava.notyourfaultkotlin.koin

import androidx.room.Room
import com.incava.notyourfaultkotlin.ShelterViewModel
import com.incava.notyourfaultkotlin.database.ShelterDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    single {
        Room.databaseBuilder(androidContext(), ShelterDatabase::class.java, "shelter_database")
            .build()
    }
    factory { ShelterViewModel(get()) }

}
