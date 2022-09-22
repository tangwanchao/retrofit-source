package me.twc.source.observer.paging

/**
 * @author 唐万超
 * @date 2022/09/22
 */
interface IPagination<T> {
    /**
     * 是否有更多数据
     *
     * @return [true : 没有更多数据]
     *         [false: 有更多数据]
     */
    fun noMoreData():Boolean

    /**
     * 获取分页的数据
     */
    fun getPagingDataList():List<T>
}