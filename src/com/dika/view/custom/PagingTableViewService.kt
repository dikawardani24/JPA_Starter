package com.dika.view.custom

import com.dika.activity.Activity

/**
 * This is a interface to make paging data on table working
 * A findActivity that having table inside should implement this
 * service if you wish to have pagination on the table
 *  @author  Dika Wardani
 */
interface PagingTableViewService {
    val activity: Activity<*>

    /**
     * counting total data that a table should have
     * @return  total data
     */
    fun countData(): Int

    /**
     * insert data start from first result and until max Result
     * @param   firstResult start insert data from
     * @param   maxResult set max data to be display on table
     */
    fun insertData(firstResult: Int, maxResult: Int)
}