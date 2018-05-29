package com.dika

class Manager<K, T: Any> {
    private val lastUsed = HashMap<K, T>()

    /**
     * Load an object which is related to the given key.
     * If the key is not exist from last used object then it will return to null.
     * @param   key the key to find object from last used object map
     * @return  instance of [T] if the key is exists
     *          `null` otherwise
     * @author  dikawardani24@gmail.com
     */
    fun load(key: K): T? {
        return lastUsed.filterKeys { it == key }[key]
    }

    /**
     * Add an object to be manageable in this manager.
     * Last used object has a type of [HashMap], so every stored key must unique
     * @param   key the key to be stored in last used object map
     * @param   value the value which related to the key
     * @author  dikawardani24@gmail.com
     */
    fun add(key: K, value: T) {
        lastUsed[key] = value
    }
}