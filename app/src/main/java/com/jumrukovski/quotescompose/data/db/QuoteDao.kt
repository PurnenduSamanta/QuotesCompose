package com.jumrukovski.quotescompose.data.db

import androidx.room.*
import com.jumrukovski.quotescompose.data.model.entity.FavouriteQuoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {

    @Query("SELECT * FROM FavouriteQuoteEntity")
    fun getAllFavouriteQuotesAsync(): Flow<List<FavouriteQuoteEntity>>

    @Query("SELECT * FROM FavouriteQuoteEntity WHERE id=:id LIMIT 1")
    fun getFavouriteQuoteAsync(id: String): Flow<FavouriteQuoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavouriteQuote(entity: FavouriteQuoteEntity)

    @Delete
    fun deleteFavouriteQuote(entity: FavouriteQuoteEntity)
}