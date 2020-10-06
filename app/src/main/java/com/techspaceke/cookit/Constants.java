package com.techspaceke.cookit;

public class Constants {
    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1";
    public static final String MEALDB_SEARCH_URL = "https://www.themealdb.com/api/json/v1/1/search.php";
    public static final String MEALDB_CATEGORIES_URL = BASE_URL + "/categories.php";
    public static final String MEALDB_LOOKUP_URL = BASE_URL + "/lookup.php";
    public static final String MEALDB_FILTER_URL = BASE_URL + "/filter.php";
    public static final String MEALDB_NAME_QUERY_PARAM = "s";
    public static final String MEALDB_INGREDIENT_QUERY_PARAM = "i";
    public static final String MEALDB_CATEGORY_QUERY_PARAM = "c";
    public static final String MEALDB_AREA_QUERY_PARAM = "a";

    public static final String PREFERENCES_RECIPE_KEY = "recipe";
    public static final String FIREBASE_CHILD_SEARCHED_RECIPES = "searchedRecipes";
    public static final String FIREBASE_CHILD_RECIPES = "recipes";
}
