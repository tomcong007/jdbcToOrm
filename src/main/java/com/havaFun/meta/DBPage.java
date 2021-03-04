package com.havaFun.meta;
import java.util.List;
public class DBPage {
    public static final int NUMBERS_PER_PAGE = 10;
    public int numPerPage;
    public int totalRows;
    public int totalPages;
    public int currentPage;
    public int startIndex;
    public int lastIndex;
    private List<DataRow> dataList;

    public DBPage(int currentPage, int numPerPage) {
        this.numPerPage = numPerPage;
        this.currentPage = currentPage;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
        this.calcTotalPages();
        this.calcStartIndex();
        this.calcLastIndex();
    }


    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    private void calcTotalPages() {
        if (this.totalRows % this.numPerPage == 0) {
            this.totalPages = this.totalRows / this.numPerPage;
        } else {
            this.totalPages = this.totalRows / this.numPerPage + 1;
        }

    }

    private void calcStartIndex() {
        this.startIndex = (this.currentPage - 1) * this.numPerPage;
    }

    private void calcLastIndex() {
        if (this.totalRows < this.numPerPage) {
            this.lastIndex = this.totalRows;
        } else if (this.totalRows % this.numPerPage != 0 && (this.totalRows % this.numPerPage == 0 || this.currentPage >= this.totalPages)) {
            if (this.totalRows % this.numPerPage != 0 && this.currentPage == this.totalPages) {
                this.lastIndex = this.totalRows;
            }
        } else {
            this.lastIndex = this.currentPage * this.numPerPage;
        }

    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public int getNumPerPage() {
        return this.numPerPage;
    }

    public List getData() {
        return this.dataList != null && this.dataList.size() > this.getNumPerPage() ? this.dataList.subList(this.getStartIndex(), this.getLastIndex()) : this.dataList;
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    public int getTotalRows() {
        return this.totalRows;
    }

    public int getStartIndex() {
        return this.startIndex;
    }

    public int getLastIndex() {
        return this.lastIndex;
    }

    public void setData(List dataList) {
        this.dataList = dataList;
    }
}
