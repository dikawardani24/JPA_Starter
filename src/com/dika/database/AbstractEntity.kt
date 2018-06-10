package com.dika.database

import java.io.Serializable

abstract class AbstractEntity<P: Number>: Serializable, Cloneable, Comparable<AbstractEntity<P>> {
    abstract var id: P?

    override fun toString(): String {
        return "$javaClass ID : $id"
    }

    @Throws(CloneNotSupportedException::class)
    public
    override fun clone(): Any = super.clone()

    override fun compareTo(other: AbstractEntity<P>): Int {
        val otherId = (other.id.takeIf { it != null } ?: 0).toInt()
        val thisId = (id.takeIf { it != null } ?: 0).toInt()

        return thisId.compareTo(otherId)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AbstractEntity<*>) return false
        if (other.javaClass != javaClass) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}