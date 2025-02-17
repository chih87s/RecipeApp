package com.db.recipeapp.core.di

import android.content.Context
import com.db.recipeapp.core.data.mappers.RecipeMapper
import com.db.recipeapp.core.data.repositories.RecipeRepo
import com.db.recipeapp.core.data.repositories.RecipeRepoImpl
import com.db.recipeapp.core.domain.RecipeUseCase
import com.db.recipeapp.core.domain.RecipeUseCaseImpl
import com.db.recipeapp.helper.JsonParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideJsonParser(@ApplicationContext context: Context): JsonParser {
        return JsonParser(context)
    }

    @Provides
    @Singleton
    fun provideRecipeRepository(jsonParser: JsonParser): RecipeRepo {
        return RecipeRepoImpl(jsonParser)
    }

    @Provides
    @Singleton
    fun provideRecipeUseCase(repository: RecipeRepo, recipeMapper: RecipeMapper): RecipeUseCase {
        return RecipeUseCaseImpl(repository, recipeMapper)
    }

    @Provides
    @Singleton
    fun provideRecipeMapper(): RecipeMapper {
        return RecipeMapper()
    }

}