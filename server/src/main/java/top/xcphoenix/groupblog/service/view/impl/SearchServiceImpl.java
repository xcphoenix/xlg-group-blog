package top.xcphoenix.groupblog.service.view.impl;

import org.springframework.stereotype.Service;
import top.xcphoenix.groupblog.manager.dao.SearchManager;
import top.xcphoenix.groupblog.model.vo.BlogData;
import top.xcphoenix.groupblog.model.vo.SearchData;
import top.xcphoenix.groupblog.service.view.BlogDataService;
import top.xcphoenix.groupblog.service.view.SearchService;

import java.util.List;

/**
 * @author      xuanc
 * @date        2020/1/26 下午8:05
 * @version     1.0
 */
@Service
public class SearchServiceImpl implements SearchService {

    private SearchManager searchManager;
    private BlogDataService blogDataService;

    public SearchServiceImpl(SearchManager searchManager, BlogDataService blogDataService) {
        this.searchManager = searchManager;
        this.blogDataService = blogDataService;
    }

    @Override
    public SearchData searchAsKeyword(String keyword, int pageSize, int pageOffset) {
        SearchData searchData = new SearchData();
        List<BlogData> blogDataList =  searchManager.searchBlogsAsKeywords(keyword, pageSize, pageOffset);
        blogDataList = blogDataService.generateBlogDataLists(blogDataList, null);
        searchData.setBlogDataList(blogDataList);
        searchData.setKeyword(keyword);
        return searchData;
    }

}
