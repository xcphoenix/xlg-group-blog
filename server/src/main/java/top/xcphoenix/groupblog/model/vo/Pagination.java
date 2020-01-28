package top.xcphoenix.groupblog.model.vo;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页
 * @author      xuanc
 * @date        2020/1/20 上午9:22
 * @version     1.0
 */
@Getter
@NoArgsConstructor
public class Pagination {

    private int pageSize;
    private int pageTotal;
    private int currentPage;
    private boolean hasPrev;
    private boolean hasNext;
    private String baseLink;
    private List<PageLink> pageLinks;

    public Pagination(int pageTotal, int currentPage, int pageSize, String baseLink) {
        this.pageTotal = pageTotal;
        this.currentPage = currentPage;
        this.pageSize = pageSize;

        if (!baseLink.contains("?")) {
            this.baseLink = baseLink + "?";
        } else {
            this.baseLink = baseLink + "&";
        }

        this.hasPrev = currentPage - 1 > 0;
        this.hasNext = currentPage + 1 <= pageTotal;
        this.pageLinks = makeLinks();
    }

    public String getPrevLink() {
        return hasPrev ? getLink(currentPage - 1) : null;
    }

    public String getNextLink() {
        return hasNext ? getLink(currentPage + 1) : null;
    }

    private String getLink(int page) {
        return baseLink + "pageNum=" + page + "&pageSize=" + this.pageSize;
    }

    private List<PageLink> makeLinks() {
        int designShowNum = 5;
        int middleNum = (designShowNum + 1) / 2;

        List<PageLink> pageLinks = new ArrayList<>(5);

        int baseValue;
        int count;

        if (this.pageTotal <= designShowNum) {
            baseValue = 0;
            count = pageTotal;
        } else if (currentPage <= middleNum) {
            baseValue = 0;
            count = designShowNum;
        } else if (currentPage + middleNum - 1 <= pageTotal) {
            baseValue = currentPage - middleNum;
            count = designShowNum;
        } else {
            baseValue = pageTotal - designShowNum;
            count = designShowNum;
        }

        for (int i = 1; i <= count; i++) {
            int pageNum = baseValue + i;
            pageLinks.add(new PageLink(pageNum, getLink(pageNum)));
        }

        return pageLinks;
    }

}