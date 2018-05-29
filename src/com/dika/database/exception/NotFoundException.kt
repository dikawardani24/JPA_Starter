package com.dika.database.exception

import com.dika.SystemException
import com.dika.database.AbstractEntity

class NotFoundException: SystemException {
    constructor() : super("No Data on database server")

    constructor(message: String) : super(message)

    constructor(abstractEntity: AbstractEntity<*>) : super("Couldn't find data of ${abstractEntity.javaClass} with ID : ${abstractEntity.id}")

    constructor(cause: Throwable) : super("Couldn't find data", cause)
}