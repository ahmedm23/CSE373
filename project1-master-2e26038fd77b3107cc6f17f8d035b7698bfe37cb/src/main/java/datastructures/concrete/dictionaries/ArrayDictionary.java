package datastructures.concrete.dictionaries;

import java.util.Arrays;

import datastructures.interfaces.IDictionary;
import misc.exceptions.NoSuchKeyException;

/**
 * See IDictionary for more details on what this class should do
 */
public class ArrayDictionary<K, V> implements IDictionary<K, V> {
    // You may not change or rename this field: we will be inspecting
    // it using our private tests.
    private Pair<K, V>[] pairs;
    private int size;
    
    // You're encouraged to add extra fields (and helper methods) though!

    public ArrayDictionary() {
    		size = 0;
        pairs = makeArrayOfPairs(10);
    }

    /**
     * This method will return a new, empty array of the given size
     * that can contain Pair<K, V> objects.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private Pair<K, V>[] makeArrayOfPairs(int arraySize) {
        // It turns out that creating arrays of generic objects in Java
        // is complicated due to something known as 'type erasure'.
        //
        // We've given you this helper method to help simplify this part of
        // your assignment. Use this helper method as appropriate when
        // implementing the rest of this class.
        //
        // You are not required to understand how this method works, what
        // type erasure is, or how arrays and generics interact. Do not
        // modify this method in any way.
        return (Pair<K, V>[]) (new Pair[arraySize]);

    }

    @Override
    public V get(K key) {
	    	return pairs[findIndex(key)].value;
    }

    @Override
    public void put(K key, V value) {
    		Pair<K, V> pair = new Pair<K, V>(key, value);
    		
    		try {
    			int index = findIndex(key);
    			pairs[index] = pair;
    			
    		} catch (NoSuchKeyException e) {
    			if (size  >= pairs.length) {
    				Pair<K, V>[] largerPairs = makeArrayOfPairs(pairs.length * 2); 
    				System.arraycopy(pairs, 0, largerPairs, 0, pairs.length);
    				pairs = largerPairs;
        		}
        		
        		pairs[size] = pair;
        		size++;
    		}
    }
    
    /**
     * Returns the index corresponding to the given key.
     *
     *  @throws NoSuchKeyException if the dictionary does not contain the given key.
     *  @throws NullPointerException if the dictionary is empty.
     */
    private int findIndex(K key) {
    		for(int i = 0; i < size; i++) {
	    		if (pairs[i].key == key || pairs[i].key.equals(key))
	    			return i;
	    }
    		
    		throw new NoSuchKeyException();
    }

    @Override
    public V remove(K key) {
    		int index = findIndex(key);
    		V value = pairs[index].value;
    		
    		for(int i = index; i < size - 1; i++) {
    			pairs[i] = pairs[i+1];
    		}
    		
    		pairs[size - 1] = null;
    		size--;
    		return value;
    }

    @Override
    public boolean containsKey(K key) {
    		try {
			findIndex(key);
			return true;	
		} catch (NoSuchKeyException e) {
			return false;
    		}
    }

    @Override
    public int size() {
        return size;
    }
    
    @Override
    public String toString() {
        return Arrays.toString(pairs);
    }

    private static class Pair<K, V> {
        public K key;
        public V value;

        // You may add constructors and methods to this class as necessary.
        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return this.key + "=" + this.value;
        }
    }
}