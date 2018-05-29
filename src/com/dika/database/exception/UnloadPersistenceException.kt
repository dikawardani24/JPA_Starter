package com.dika.database.exception

import com.dika.System
import com.dika.SystemException

class UnloadPersistenceException:
        SystemException("Not loaded persistence config with name : ${System.persitenceName} ")