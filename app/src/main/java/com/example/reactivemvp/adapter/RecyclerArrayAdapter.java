package com.example.reactivemvp.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Array adapter for Recycler view. The ArrayAdapter for list view cannot be used.
 * Inspiration from:
 * https://gist.github.com/passsy/f8eecc97c37e3de46176
 *
 * @param <T> -
 * @param <V> -
 */
public abstract class RecyclerArrayAdapter<T, V extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<V> {

    private List<T> objectList;

    public RecyclerArrayAdapter(final List<T> objects) {
        this.objectList = objects;
    }

    /**
     * Adds the specified object at the end of the array.
     *
     * @param object The object to add at the end of the array.
     */
    public void add(final T object) {
        objectList.add(object);
        notifyItemInserted(getItemCount() - 1);
    }

    /**
     * Remove all elements from the list.
     */
    public void clear() {
        final int size = getItemCount();
        objectList.clear();
        notifyItemRangeRemoved(0, size);
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    public T getItem(final int position) {
        return objectList.get(position);
    }

    public long getItemId(final int position) {
        return position;
    }

    /**
     * Returns the position of the specified item in the array.
     *
     * @param item The item to retrieve the position of.
     * @return The position of the specified item.
     */
    public int getPosition(final T item) {
        return objectList.indexOf(item);
    }

    /**
     * Inserts the specified object at the specified index in the array.
     *
     * @param object The object to insert into the array.
     * @param index  The index at which the object must be inserted.
     */
    public void insert(final T object, int index) {
        objectList.add(index, object);
        notifyItemInserted(index);

    }

    /**
     * Removes the specified object from the array.
     *
     * @param object The object to remove.
     */
    public void remove(T object) {
        final int position = getPosition(object);
        objectList.remove(object);
        notifyItemRemoved(position);
    }

    /**
     * Sorts the content of this adapter using the specified comparator.
     *
     * @param comparator The comparator used to sort the objectList contained in this adapter.
     */
    public void sort(Comparator<? super T> comparator) {
        Collections.sort(objectList, comparator);
        notifyItemRangeChanged(0, getItemCount());
    }

    /**
     * Replace the current list backing this adapter.
     *
     * @param newList - new list
     */
    public void replace(List<T> newList) {
        objectList = newList;
        notifyDataSetChanged();
    }
}
