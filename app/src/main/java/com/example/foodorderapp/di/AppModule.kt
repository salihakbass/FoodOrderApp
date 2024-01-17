package com.example.foodorderapp.di

import android.content.Context
import androidx.room.Room
import com.example.foodorderapp.data.datasource.FavoriteDataSource
import com.example.foodorderapp.data.datasource.ProductsDataSource
import com.example.foodorderapp.data.repo.FavoriteRepository
import com.example.foodorderapp.data.repo.ProductsRepository
import com.example.foodorderapp.retrofit.ApiUtils
import com.example.foodorderapp.retrofit.ProductDao
import com.example.foodorderapp.room.Database
import com.example.foodorderapp.room.FavDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideProductsDataSource(pdao : ProductDao,fds : FavoriteDataSource) : ProductsDataSource {
        return ProductsDataSource(pdao,fds)
    }
    @Provides
    @Singleton
    fun provideProductsRepository(pds: ProductsDataSource) : ProductsRepository {
        return ProductsRepository(pds)
    }
    @Provides
    @Singleton
    fun provideProductDao() : ProductDao {
        return ApiUtils.getProductDao()
    }
    @Provides
    @Singleton
    fun provideFavDao(@ApplicationContext context: Context) : FavDao {
        val db = Room.databaseBuilder(context,Database::class.java,"favfood.sqlite")
            .createFromAsset("favfood.sqlite").build()
        return db.getFavDao()
    }
    @Provides
    @Singleton
    fun provideFavDataSource(fdao : FavDao) : FavoriteDataSource {
        return FavoriteDataSource(fdao)
    }
    @Provides
    @Singleton
    fun provideFavRepository(fds: FavoriteDataSource) : FavoriteRepository {
        return FavoriteRepository(fds)
    }
}