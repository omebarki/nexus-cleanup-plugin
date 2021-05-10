package omar.mebarki.maven.plugins.nexus;


import org.junit.Test;
import omar.mebarki.maven.plugins.nexus.repository.URLSUtils;

import static org.junit.Assert.*;

/**
 * Created by omebarki on 22/05/2017.
 */
public class URLSUtilsTest {
    @Test
    public void addSlashToUrl() throws Exception {
        String url = "http://ddd/dd";
        String url2 = url + "/";
        assertEquals(url2, URLSUtils.addSlashToUrl(url));
        assertEquals(url2, URLSUtils.addSlashToUrl(url2));
    }

    @Test
    public void removeSlashToUrl() throws Exception {
        String url = "http://ddd/dd";
        String url2 = url + "/";
        assertEquals(url, URLSUtils.removeSlashToUrl(url));
        assertEquals(url, URLSUtils.removeSlashToUrl(url2));
    }

}
