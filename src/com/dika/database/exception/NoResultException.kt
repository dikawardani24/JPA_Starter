package com.dika.database.exception

import com.dika.SystemException
import javax.persistence.NoResultException

class NoResultException(noResultException: NoResultException):
        SystemException("No incoming result from database server", noResultException)