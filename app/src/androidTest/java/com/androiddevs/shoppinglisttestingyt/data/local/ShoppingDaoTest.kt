package com.androiddevs.shoppinglisttestingyt.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.androiddevs.shoppinglisttestingyt.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ShoppingDaoTest {


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ShoppingItemDatabase
    private lateinit var dao: ShoppingDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingItemDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.shoppingDao()
    }

    @After
    fun tearDown(){
        database.close()
    }


    @Test
    fun insertShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItem(id = 1, name = "tickect", amount = 1, price = 1f, imageUrl = "url" )
        dao.insertItem(shoppingItem)

        val allShopingItems = dao.observeAllShoppingItem().getOrAwaitValue()
        assertThat(allShopingItems).contains(shoppingItem)
    }


    @Test
    fun deleteShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItem(id = 1, name = "tickect", amount = 1, price = 1f, imageUrl = "url" )
        dao.insertItem(shoppingItem)
        dao.deleteShoppingItem(shoppingItem)
        val allShopingItems = dao.observeAllShoppingItem().getOrAwaitValue()

        assertThat(allShopingItems).doesNotContain(shoppingItem)
    }


    @Test
    fun observeAndThrowTotalPrice() =  runBlockingTest {
        val shoppingItem = ShoppingItem(id = 1, name = "tickect", amount = 5, price = 1f, imageUrl = "url" )
        val shoppingItem2 = ShoppingItem(id = 2, name = "tickect", amount = 1, price = 10f, imageUrl = "url" )
        val shoppingItem3 = ShoppingItem(id = 3, name = "tickect", amount = 2, price = 100f, imageUrl = "url" )
        dao.insertItem(shoppingItem)
        dao.insertItem(shoppingItem2)
        dao.insertItem(shoppingItem3)

        val getTotalPrice = dao.observeTotalPrice().getOrAwaitValue()

        assertThat(getTotalPrice).isEqualTo(5*1f + 1*10f + 2*100f)


    }

}