package edu.common.cloud.dream.dto;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Date: 2023-11-08 16:19
 * @Author： shenliuming
 * @return：
 */
public class PageQueryUtil extends LinkedHashMap<String,Object> {

    //当前页码
    private int page;
    //每页条数
    private int limit;

    public PageQueryUtil(Map<String, Object> ReqDtos) {
        this.putAll(ReqDtos);
        //分页参数
        this.page = Integer.parseInt(ReqDtos.get("page").toString());
        this.limit = Integer.parseInt(ReqDtos.get("limit").toString());
        this.put("start", (page - 1) * limit);
        this.put("page", page);
        this.put("limit", limit);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
