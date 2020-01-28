package top.xcphoenix.groupblog.processor;

import com.rometools.rome.io.FeedException;
import top.xcphoenix.groupblog.expection.processor.ProcessorException;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * @author xuanc
 * @version 1.0
 * @date 2020/1/10 下午7:06
 */
@FunctionalInterface
public interface Processor {

    /**
     * 处理页面
     *
     * @param url 待处理的页面
     * @return result data
     * @throws ProcessorException 页面处理产生的异常
     */
    Object processor(String url) throws ProcessorException;

}
