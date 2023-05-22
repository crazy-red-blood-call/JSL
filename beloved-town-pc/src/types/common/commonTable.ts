export interface TableInfo {
    tableList: any[],
    pageInfo: {
        pageSize: number,
        pageNo: number,
        currentPage: number,
        total: number,
    }
}