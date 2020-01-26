package top.xcphoenix.groupblog.service.view.impl;

import org.springframework.stereotype.Service;
import top.xcphoenix.groupblog.mapper.ArchiveMapper;
import top.xcphoenix.groupblog.model.dto.ArchiveBlogItem;
import top.xcphoenix.groupblog.model.dto.ArchiveBlogs;
import top.xcphoenix.groupblog.service.view.ArchiveService;
import top.xcphoenix.groupblog.service.view.LinkGeneratorService;

import java.util.List;

/**
 * @author      xuanc
 * @date        2020/1/25 下午10:02
 * @version     1.0
 */
@Service
public class ArchiveServiceImpl implements ArchiveService {

    private ArchiveMapper archiveMapper;
    private LinkGeneratorService linkGeneratorService;

    public ArchiveServiceImpl(ArchiveMapper archiveMapper, LinkGeneratorService linkGeneratorService) {
        this.archiveMapper = archiveMapper;
        this.linkGeneratorService = linkGeneratorService;
    }

    @Override
    public List<ArchiveBlogs> getArchive(int pageNum, int pageSize) {
        List<ArchiveBlogs> archiveBlogs = archiveMapper.getBaseBlogIds(pageSize, (pageNum - 1) * pageSize);
        for (ArchiveBlogs archive : archiveBlogs) {
            archive.setBlogItems(
                    archiveMapper.getBlogsFromGroup(archive.getBlogIds())
            );
            // set url
            for (ArchiveBlogItem item : archive.getBlogItems()) {
                long uid = item.getUid();
                long blogId = item.getBlogId();
                item.setUserLink(linkGeneratorService.getUserLink(uid));
                item.setBlogLink(linkGeneratorService.getBlogLink(blogId));
            }
        }
        return archiveBlogs;
    }

    @Override
    public List<ArchiveBlogs> getArchiveAsUser(long uid, int pageNum, int pageSize) {
        List<ArchiveBlogs> archiveBlogs = archiveMapper
                .getBaseBlogIdsAsUser(pageSize, (pageNum - 1) * pageSize, uid);
        for (ArchiveBlogs archive : archiveBlogs) {
            archive.setBlogItems(
                    archiveMapper.getBlogsFromGroup(archive.getBlogIds())
            );
            // set url
            for (ArchiveBlogItem item : archive.getBlogItems()) {
                long blogId = item.getBlogId();
                item.setBlogLink(linkGeneratorService.getBlogLink(blogId));
            }
        }
        return archiveBlogs;
    }

}
