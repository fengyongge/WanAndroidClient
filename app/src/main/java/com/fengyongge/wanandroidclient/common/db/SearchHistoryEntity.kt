package com.fengyongge.wanandroidclient.common.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Long,
    @ColumnInfo(name = "searchContent") val searchContent: String?
)