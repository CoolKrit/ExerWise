package com.example.exerwise.presentation

import android.content.Context
import com.example.exerwise.data.model.Exercise
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListDataManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("ItemListSharedPreferences", Context.MODE_PRIVATE)
    private val keyItemList = "item_list"

    fun saveItemList(itemList: List<Exercise>) {
        val editor = sharedPreferences.edit()
        val serializedList = Gson().toJson(itemList)
        editor.putString(keyItemList, serializedList)
        editor.apply()
    }

    fun loadItemList(): List<Exercise> {
        val serializedList = sharedPreferences.getString(keyItemList, null)
        return if (serializedList != null) {
            Gson().fromJson(serializedList, object : TypeToken<List<Exercise>>() {}.type)
        } else {
            listOf()
        }
    }
}