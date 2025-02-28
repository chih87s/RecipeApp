package com.db.recipeapp.core.di

import android.content.Context
import com.db.recipeapp.core.data.mappers.RecipeMapper
import com.db.recipeapp.core.data.repositories.JsonRecipeDataSource
import com.db.recipeapp.core.data.repositories.RecipeDataSource
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
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideJsonParser(): JsonParser {
        return JsonParser()
    }

    @Provides
    @Singleton
    @JsonDataSource
    fun provideDataSource(jsonParser: JsonParser): RecipeDataSource {
        return JsonRecipeDataSource(jsonParser)
    }

    @Provides
    @Singleton
    fun provideRecipeRepository(@JsonDataSource recipeDataSource: RecipeDataSource): RecipeRepo {
        return RecipeRepoImpl(recipeDataSource)
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


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class JsonDataSource


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteDataSourceQualifier
