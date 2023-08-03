package com.hesham.ecommerceapp.domain.entities

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
@Entity(tableName = "product")
data class Product(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo("title") val title: String = "",
    @ColumnInfo("price") val price: Double = 0.0,
    @ColumnInfo("category") val category: String = "",
    @ColumnInfo("description") val description: String = "",
    @ColumnInfo("image") val image: String = "",
    @ColumnInfo("quantity") val quantity: Int = 0,
) : Serializable
