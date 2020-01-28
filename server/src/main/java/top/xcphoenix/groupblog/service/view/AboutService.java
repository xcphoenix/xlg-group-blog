package top.xcphoenix.groupblog.service.view;

import top.xcphoenix.groupblog.model.vo.PostData;

/**
 * @author xuanc
 * @version 1.0
 * @date 2020/1/23 下午2:28
 */
public interface AboutService {

    /**
     * 获取关于信息
     *
     * @return 关于信息
     */
    PostData getAbout();

}
