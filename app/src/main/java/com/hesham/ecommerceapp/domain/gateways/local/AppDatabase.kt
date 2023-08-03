package com.hesham.ecommerceapp.domain.gateways.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hesham.ecommerceapp.domain.entities.Product
import com.hesham.ecommerceapp.domain.gateways.local.dao.ProductDao

@Database(entities = [Product::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDto(): ProductDao
}