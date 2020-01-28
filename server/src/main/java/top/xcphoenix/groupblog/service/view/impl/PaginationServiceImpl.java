package top.xcphoenix.groupblog.service.view.impl;

import org.springframework.stereotype.Service;
import top.xcphoenix.groupblog.manager.dao.SearchManager;
import top.xcphoenix.groupblog.manager.dao.StaticsNumsManager;
import top.xcphoenix.groupblog.model.vo.Pagination;
import top.xcphoenix.groupblog.service.view.PaginationService;

/**
 * @author      xuanc
 * @date        2020/1/25 下午2:23
 * @version     1.0
 */ 
@Service
public class PaginationServiceImpl implements PaginationService {

    private StaticsNumsManager staticsNumsManager;
    private SearchManager searchManager;

    public PaginationServiceImpl(StaticsNumsManager staticsNumsManager, SearchManager searchManager) {
        this.staticsNumsManager = staticsNumsManager;
        this.searchManager = searchManager;
    }

    @Override
    public Pagination getPagination(int pageNum, int pageSize, String baseLink) {
        long blogNums = staticsNumsManager.getSiteStaticsNum().getBlogNum();
        int pageTotal = (int)(blogNums / pageSize);
        return new Pagination(pageTotal, pageNum, pageSize, baseLink);
    }

    @Override
    public Pagination getPaginationAsUser(int pageNum, int pageSize, String baseLink, long uid) {
        long blogNums = staticsNumsManager.getUserStaticsNum(uid);
        int pageTotal = (int)(blogNums / pageSize);
        return new Pagination(pageTotal, pageNum, pageSize, baseLink);
    }

    @Override
    public Pagination getPaginationAsSearch(int pageNum, int pageSize, String baseLink, String keyword) {
        long blogNums = searchManager.getSearchDataNum(keyword);
        int pageTotal = (int)(blogNums / pageSize);
        return new Pagination(pageTotal, pageNum, pageSize, baseLink);
    }

}
