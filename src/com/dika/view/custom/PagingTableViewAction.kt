package com.dika.view.custom

/**
 * This class is meant to be a handler of data pagination on table.
 * To use this class:
 * * first you have to implement [PagingTableViewService] inside your findActivity class
 * * then implement [PagingTableView] inside your UI Class
 * @param   pagingTableViewService  the service for handling counting total data and insert data
 * @param   pagingTableView the service contain components of pagination
 * @author  Dika Wardani
 */
class PagingTableViewAction(private val pagingTableViewService: PagingTableViewService, private val pagingTableView: PagingTableView) {
    private var lastFirstResult: Int = 0
    private var maxResults: Int = 20
    private var currentPage: Int = 0
    private var totalPage: Int = 0
    private var totalData: Int = 0

    /**
     * Specified the max data to be display inside table
     * @param   pagingTableViewService  the service for handling counting total data and insert data
     * @param   pagingTableView the service contain components of pagination
     * @param   maxResults set the max data to be display on the table
     */
    constructor(pagingTableViewService: PagingTableViewService, pagingTableView: PagingTableView, maxResults: Int) :
            this(pagingTableViewService, pagingTableView) {
        this.maxResults = maxResults
    }

    init {
        pagingTableView.run {
            nextButton.addActionListener({ moveTo(page = Page.NEXT) })
            previousButton.addActionListener({ moveTo(page = Page.PREV) })
            firstPageButton.addActionListener({ moveTo(page = Page.FIRST) })
            lastPageButton.addActionListener({ moveTo(page = Page.LAST) })
            refreshButton.addActionListener({ refreshPage() })
        }
    }

    /**
     * move to the last page
     */
    fun toLastPage() { moveTo(page = Page.LAST) }

    /**
     * move to page 1
     */
    fun toFirstPage() { moveTo(page = Page.FIRST) }

    /**
     * reload data of the current page
     */
    fun refreshPage() {
        /*
            val progress = createProgressDialog(pagingTableService.activity.windowView?.rootContainer,
                    "Muat Ulang", "Memuat Data...").apply {
                isVisible = true
            }*/

        if (isDataEmpty()) {
            disablePaging()
            //progress.dispose()
            return
        }
        countTotalPage()
        pagingTableViewService.insertData(firstResult = lastFirstResult, maxResult = maxResults)
        printPage()
        //progress.dispose()
    }

    /**
     * check total data of this pagination
     */
    private fun isDataEmpty(): Boolean {
        totalData = pagingTableViewService.countData()
        pagingTableView.totalDataField.text = "$totalData"
        return totalData <= 0
    }

    /**
     * count total page that this pagination should have
     */
    private fun countTotalPage() {
        totalPage = totalData / maxResults
        if (totalData % maxResults != 0) totalPage++
    }

    /**
     * printIcon out current active page
     * printIcon out total page
     */
    private fun printPage() {
        pagingTableView.pageLabel.text = "Hal. $currentPage Dari $totalPage Halaman"
    }

    /**
     * disable paging when there is no data in table
     * or after counting data
     */
    private fun disablePaging() {
        pagingTableView.run {
            firstPageButton.isEnabled = false
            lastPageButton.isEnabled = false
            nextButton.isEnabled = false
            previousButton.isEnabled = false
        }

        currentPage = 0
        totalPage = 0
        totalData = 0
        lastFirstResult = 0
        printPage()
    }

    /**
     * move page to desire page
     * @param   page to display
     */
    private fun moveTo(page: Page) {
        if (isDataEmpty()) {
            disablePaging()
            return
        }

        countTotalPage()
        when (page) {
            Page.NEXT -> {
                currentPage++
                lastFirstResult += maxResults
            }
            Page.PREV -> {
                currentPage--
                lastFirstResult -= maxResults
            }
            Page.LAST -> {
                currentPage = totalPage
                lastFirstResult = maxResults * (totalPage - 1)
            }
            Page.FIRST -> {
                currentPage = 1
                lastFirstResult = 0
            }
        }

        val isOnFirstPage = currentPage == 1
        val isOnLastPage = currentPage == totalPage

        pagingTableView.run {
            previousButton.isEnabled = !isOnFirstPage
            firstPageButton.isEnabled = !isOnFirstPage
            nextButton.isEnabled = !isOnLastPage
            lastPageButton.isEnabled = !isOnLastPage
        }

        pagingTableViewService.insertData(firstResult = lastFirstResult, maxResult = maxResults)
        printPage()
    }

    /**
     * Pagination helper
     */
    private enum class Page { NEXT, PREV, LAST, FIRST }
}