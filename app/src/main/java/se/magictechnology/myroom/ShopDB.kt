package se.magictechnology.myroom

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class ShopDB(ctx : Context)
{
    lateinit var shopdb : ShoppingDatabase

    init {

        shopdb = Room.databaseBuilder(
                ctx,
                ShoppingDatabase::class.java, "shopping-db"
        ).build()
    }

    @Entity
    data class ShoppingItem(
        @PrimaryKey(autoGenerate = true) val uid: Int = 0,
        @ColumnInfo(name = "shop_name") val shopName: String?,
        @ColumnInfo(name = "shop_amount") val shopAmount: Int?
    )

    @Dao
    interface ShoppingDao {

        @Query("SELECT * FROM shoppingitem")
        fun loadAll(): List<ShoppingItem>

        @Query("SELECT * FROM shoppingitem WHERE shop_amount = :number")
        fun loadAmount(number : Int): List<ShoppingItem>


        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun save(shoppingitem: ShoppingItem)

        @Delete
        fun delete(shoppingitem: ShoppingItem)

        @Update
        fun updateShopItem(vararg shopitem: ShoppingItem)
    }

    @Database(entities = arrayOf(ShoppingItem::class), version = 2)
    abstract class ShoppingDatabase : RoomDatabase() {
        abstract fun ShoppingDao(): ShoppingDao
    }
}