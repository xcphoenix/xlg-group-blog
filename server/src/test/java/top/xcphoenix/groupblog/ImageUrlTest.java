package top.xcphoenix.groupblog;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import top.xcphoenix.groupblog.utils.UrlUtil;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Slf4j
public class ImageUrlTest {
    @Test
    void testImg() throws NoSuchAlgorithmException, KeyManagementException {
        String imageUrl = "https://blog.xiyoulinux.com/avatar/10143-avatar.jpg";
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {}
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {}
                    public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
                }
        };
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, new SecureRandom());
        SSLContext.setDefault(sslContext);
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            System.out.println(imageUrl+"图片检测结果："+responseCode);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
