package com.fengyongge.wanandroidclient.common.db

import androidx.room.*

@Dao
interface SearchHistoryDao {


    @Query("SELECT * FROM searchHistoryEntity order by uid desc")
    fun getSearchContent(): MutableList<SearchHistoryEntity>

    @Query("SELECT * FROM searchHistoryEntity where uid=:id")
    fun getSearchContentById(id: Int): SearchHistoryEntity

    @Insert
    fun insertSearchContent(searchHistoryEntity: SearchHistoryEntity):Long

    @Update
    fun updateSearchContent(searchHistoryEntity: SearchHistoryEntity):Int

    @Delete()
    fun deleteSearchContent(searchHistoryEntity:SearchHistoryEntity):Int


}
