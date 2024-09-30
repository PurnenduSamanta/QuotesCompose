package com.jumrukovski.quotescompose.data.repository

import com.jumrukovski.quotescompose.data.db.LocalDB
import com.jumrukovski.quotescompose.data.mapper.mapToQuote
import com.jumrukovski.quotescompose.data.mapper.mapToQuoteEntity
import com.jumrukovski.quotescompose.data.mapper.mapToQuotes
import com.jumrukovski.quotescompose.domain.model.Quote
import com.jumrukovski.quotescompose.domain.repository.LocalRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class LocalRepositoryImpl @Inject constructor(private val localDB: LocalDB) : LocalRepository {

    override fun getAllFavouriteQuotes(): Flow<List<Quote>> =
        localDB.quoteDao().getAllFavouriteQuotes().mapToQuotes()

    override fun getFavouriteQuote(id: Int): Flow<Quote?> =
        localDB.quoteDao().getFavouriteQuote(id).mapToQuote()

    override fun addFavouriteQuote(quote: Quote) {
        val entity = quote.mapToQuoteEntity()
        localDB.quoteDao().addFavouriteQuote(entity)
    }

    override fun removeFavouriteQuote(quote: Quote) {
        val entity = quote.mapToQuoteEntity()
        localDB.quoteDao().deleteFavouriteQuote(entity)
    }
}
