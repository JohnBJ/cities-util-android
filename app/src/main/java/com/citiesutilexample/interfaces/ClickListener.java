package com.citiesutilexample.interfaces;

/**
 * Click listener interface for RecyclerView
 * @param <T>
 */
public interface ClickListener<T> {
    void onItemClick(T data);
}
