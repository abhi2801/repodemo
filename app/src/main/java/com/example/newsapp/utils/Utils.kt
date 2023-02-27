package com.example.newsapp.utils

object Utils {
    fun getNewsCategory(tabPos: Int): String {
        return when (tabPos) {
            1 -> {
                "business"
            }
            2 -> {
                "entertainment"
            }
            3 -> {
                "environment"
            }
            4 -> {
                "food"
            }
            5 -> {
                "health"
            }
            6 -> {
                "politics"
            }
            7 -> {
                "science"
            }
            8 -> {
                "sports"
            }
            9 -> {
                "technology"
            }
            10 -> {
                "top"
            }
            11 -> {
                "world"
            }
            else -> {
                ""
            }
        }
    }
}