package com.mafusaik.characters.di

import com.mafusaik.characters.data.api.RickAndMortyApi
import com.mafusaik.characters.data.repository.CharacterRepositoryImpl
import com.mafusaik.characters.domain.repository.CharacterRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CharactersModule {

    @Binds
    @Singleton
    abstract fun bindCharacterRepository(
        impl: CharacterRepositoryImpl
    ): CharacterRepository
}

@Module
@InstallIn(SingletonComponent::class)
object CharactersProvideModule {

    @Provides
    @Singleton
    fun provideRickAndMortyApi(retrofit: Retrofit): RickAndMortyApi {
        return retrofit.create(RickAndMortyApi::class.java)
    }
}