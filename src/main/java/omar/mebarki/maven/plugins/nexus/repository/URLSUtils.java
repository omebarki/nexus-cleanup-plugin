package omar.mebarki.maven.plugins.nexus.repository;

/**
 * Created by omebarki on 22/05/2017.
 */
public class URLSUtils {
    public static String addSlashToUrl(String url) {
        return url.endsWith("/") ? url : url + "/";
    }

    public static String removeSlashToUrl(String url) {
        return url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
    }

    public static String convertToAPI(String url) {
        String urlAPI = addSlashToUrl(url.replaceFirst("/content/", "/service/local/"));
        return urlAPI;
    }

    public static String convertToContentAPI(String url) {
        return addContentSuffix(convertToAPI(url));
    }

    public static String addContentSuffix(String url) {
        return addSlashToUrl(url) + "content/";
    }
}
