package com.hesham.ecommerceapp.domain.gateways.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hesham.ecommerceapp.domain.entities.Product

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product)

    @Delete
    suspend fun delete(product: Product)

    @Query("DELETE FROM product")
    suspend fun deleteAll()

    @Query("SELECT * FROM product ORDER BY id ASC")
    fun getAllProduct(): List<Product>

    @Query("SELECT * FROM product WHERE id=:productId")
    fun getProduct(productId:Int): Product?


}