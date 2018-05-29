package com.dika

/**
 * This class is meant to be a handler of data encryption
 * @param   T the type data to be encrypted
 * @param   X the output data type after data is encrypted
 * @author Dika Wardani
 */
abstract class Security<in T, out X> {
    /**
     * Encrypt the input of [T] and return the encrypted as [X]
     * @param   input set the input to be encrypted
     * @return  `encrypted` of [X]
     */
    abstract fun secure(input: T): X
}