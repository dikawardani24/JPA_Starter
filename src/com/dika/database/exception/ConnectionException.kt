package com.dika.database.exception

import com.dika.SystemException

class ConnectionException(cause: Throwable):
        SystemException("Couldn't established the connection to Database Server", cause)
