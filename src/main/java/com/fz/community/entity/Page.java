package com.fz.community.entity;

/**
 * @author zxf
 * @date 2022/6/10
 */
public class Page {

    //当前页码
    private int current = 1;
    // 显示上限
    private int limit = 10;
    //数据总数
    private int rows;
    //查询路径
    private String path;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if(current >=1){
            this.current = current;
        }

    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if(limit >= 1 && limit <= 100){
            this.limit = limit;
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        if (rows >= 0){
            this.rows = rows;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取当前页首行是第几行
     * @return
     */
    public int getOffset(){
        return (current - 1) * limit;
    }

    /**
     * 获取总页数
     * @return
     */
    public int getTotal(){
        if(rows % limit == 0){
            return rows / limit;
        }
        else{
            return rows / limit + 1;
        }
    }

    /**
     * 获得起始页
     * @return
     */
    public int getFrom(){
        int from = current - 2;
        return Math.max(from, 1);
    }

    /**
     * 获得终止页码
     * @return
     */
    public int getTo(){
        int to = current + 2;
        int total = getTotal();
        return Math.min(to, total);
    }
}
