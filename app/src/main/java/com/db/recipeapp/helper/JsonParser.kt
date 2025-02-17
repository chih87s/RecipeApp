package com.db.recipeapp.helper

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.io.IOException
import java.io.InputStreamReader
import javax.inject.Inject

class JsonParser @Inject constructor(private val context: Context) {

    fun <T> parseJsonFromFile(fileName: String, classOfT: Class<T>): T {

        return try {
            val inputStream = context.assets.open(fileName)
            InputStreamReader(inputStream).use { reader ->
                Gson().fromJson(reader, classOfT)
            }
        } catch (e: IOException) {
            throw RuntimeException("Error reading file: $fileName", e)

        } catch (e: JsonSyntaxException) {
            throw RuntimeException("Error parsing JSON in file: $fileName", e)

        }

    }
}