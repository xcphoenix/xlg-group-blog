package top.xcphoenix.groupblog.manager.dao.impl;

import org.springframework.stereotype.Service;
import top.xcphoenix.groupblog.manager.dao.SearchManager;
import top.xcphoenix.groupblog.mapper.SearchMapper;
import top.xcphoenix.groupblog.model.vo.BlogData;

import java.util.List;

/**
 * @author      xuanc
 * @date        2020/1/26 下午7:16
 * @version     1.0
 */
@Service
public class SearchManagerImpl implements SearchManager {

    private SearchMapper searchMapper;

    public SearchManagerImpl(SearchMapper searchMapper) {
        this.searchMapper = searchMapper;
    }

    @Override
    public List<BlogData> searchBlogsAsKeywords(String keyword, int pageSize, int pageOffset) {
        keyword = keyConvert(keyword);
        return searchMapper.searchBlogsAsKeyword(keyword, pageSize, pageOffset);
    }

    @Override
    public int getSearchDataNum(String keyword) {
        return searchMapper.getSearchNum(keyword);
    }

    private String keyConvert(String key) {
        String booleanSpecialFlag = "+-><()~\"";
        for (int i = 0; i < booleanSpecialFlag.length(); i++) {
            key = key.replace(booleanSpecialFlag.charAt(i), '*');
        }
        key = key.replaceAll("\\*+", "*");
        if (key.startsWith("*")) {
            key = key.substring(1);
        }
        if (!key.endsWith("*")) {
            key = key + "*";
        }
        return key;
    }

}
