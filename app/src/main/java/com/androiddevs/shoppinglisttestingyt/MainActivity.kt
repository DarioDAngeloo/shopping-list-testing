package com.androiddevs.shoppinglisttestingyt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androiddevs.shoppinglisttestingyt.repositories.ShoppingRepositoryImpl

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ShoppingRepositoryImpl.layout.activity_main)
    }
}