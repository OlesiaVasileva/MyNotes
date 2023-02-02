package com.olesix.mynotes.di

import com.olesix.mynotes.db.AppDatabase
import com.olesix.mynotes.repository.NoteRepository
import com.olesix.mynotes.viewmodel.EditViewModel
import com.olesix.mynotes.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainViewModule = module {
    viewModel {
        MainViewModel(noteRepository = get())
    }
}

val editViewModule = module {
    viewModel {
        EditViewModel(noteRepository = get())
    }
}

val repositoryModule = module {
    single { NoteRepository(AppDatabase.getDatabase(context = androidContext()).noteDao()) }
}