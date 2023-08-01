package top.xcphoenix.groupblog.service.picture;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;
import top.xcphoenix.groupblog.processor.impl.SeleniumProcessor;
import top.xcphoenix.groupblog.utils.UrlUtil;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Service("craw-picture")
@Data
@PropertySource(value = "${config-dir}/processor.properties",encoding = "utf-8")
@Slf4j
public class CrawPictureService {
    @Value("${picture.service-name}")
    String serviceName;
    @Autowired
    private ResourceLoader resourceLoader;
    @Value("${picture.down}")
    private boolean isDownPicture;
    @Value("${picture.address}")
    private String address;

    public String downPicture(Element document,String url) throws IOException {
        Elements images = document.select("img");

        for (Element image : images) {
            // 获取地址并下载
            String newPictureUrl = getPicture(image,url);
            //修改图片地址
            image.attr("src", newPictureUrl);
        }

        return document.html();
    }

    private String getPicture(Element image,String originalLink) throws IOException {
        String src = image.attr("src");
        URL url = new URL(originalLink);
        if(!src.startsWith("https://")){
            // 添加域名
            src = "https://"+url.getHost()+src;
        }

        String picture = downloadImage(src, getPictureName(UrlUtil.getAuthor(originalLink), new URL(src).getPath()));
        if(picture.startsWith("https://")){
            return picture;
        }
        return "https://"+serviceName+picture;
    }

    private String getPictureName(String author,String path) {
        int last = path.length();
        // 去除最后面的“/”
        while (last > 0){
            if(path.charAt(last-1) == '/'){
                last--;
            }else {
                break;
            }
        }

        path = path.substring(0,last);
        // 如果图片为“”，则使用此时时间命名
        if("".equals(path)){
            path = System.currentTimeMillis() +".jpg";
        }
        int start = path.lastIndexOf('/') + 1;
        return author+"-"+path.substring(start);
    }

    private String downloadImage(String imageUrl, String fileName) throws IOException {
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if(!path.exists()) {
            path = new File("");
        }
        File upload = new File(path.getAbsolutePath(),address+"/img/");
        if(!upload.exists()) {
            upload.mkdirs();
        }
        // 检测图片是否已经下载(只验证是否存在该文件)
        File file = new File(upload,fileName);
        log.info("new picture: "+file.getAbsolutePath());
        if(file.exists()){
            return "/img/" + fileName;
        }

        // 保存图片,若保存出错，或设为不保存，则返回原始图片url
        try {
            if(isDownPicture){
                InputStream inputStream = new URL(imageUrl).openStream();
                byte[] imageData = FileCopyUtils.copyToByteArray(inputStream);
                FileOutputStream outputStream = new FileOutputStream(file, true);
                outputStream.write(imageData);
                inputStream.close();
                outputStream.close();
            }else{
                return imageUrl;
            }
        }catch (Exception e){
            return imageUrl;
        }

        return "/img/" + fileName;
    }
}
