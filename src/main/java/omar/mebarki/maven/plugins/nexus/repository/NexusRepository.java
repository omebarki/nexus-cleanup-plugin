package omar.mebarki.maven.plugins.nexus.repository;

import omar.mebarki.maven.plugins.nexus.filter.NexusArtifactsFilter;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * Created by omebarki on 19/05/2017.
 */
public class NexusRepository {
    private final String nexusURL;

    private final String nexusUserName;

    private final String nexusPassword;

    private final NexusRepoXMLParser nexusRepoXMLParser;

    private final NexusArtifactsFilter nexusArtifactsFilter;

    private final String authEncoded;

    public NexusRepository(final String nexusURL, final String nexusUserName, final String nexusPassword, NexusRepoXMLParser nexusRepoXMLParser, NexusArtifactsFilter nexusArtifactsFilter) {
        this.nexusURL = URLSUtils.addSlashToUrl(nexusURL);
        this.nexusUserName = nexusUserName;
        this.nexusPassword = nexusPassword;
        this.nexusRepoXMLParser = nexusRepoXMLParser;
        this.nexusArtifactsFilter = nexusArtifactsFilter;
        this.authEncoded = Base64.getEncoder().encodeToString((nexusUserName + ":" + nexusPassword).getBytes(StandardCharsets.UTF_8));
    }

    public void cleanUP(String artifactPath) throws Exception {
        List<NexusArtifactVersion> artifactsVersions = this.getArtifactVersions(artifactPath);
        this.cleanArtifactsVersions(artifactsVersions);
    }

    public List<NexusArtifactVersion> getArtifactVersions(String artifactPath) throws Exception {
        List<NexusArtifactVersion> nexusArtifactsVersions = new ArrayList<>();
        String artifactURL = (nexusURL + artifactPath);
        artifactURL = URLSUtils.addSlashToUrl(artifactURL);
        URL url = new URL(artifactURL);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestProperty("Authorization", "Basic " + this.authEncoded);
        urlConnection.setRequestProperty("Content-type", "application/xml");
        try (InputStream inputStream = urlConnection.getInputStream()) {
            nexusArtifactsVersions = this.nexusRepoXMLParser.parse(inputStream);
        }
        urlConnection.disconnect();
        return nexusArtifactsVersions;
    }

    public void cleanArtifactsVersions(List<NexusArtifactVersion> artifactVersions) {
        List<NexusArtifactVersion> toDelete = nexusArtifactsFilter.filter(artifactVersions);
        toDelete.forEach(artfact -> {
            try {
                URL url = new URL(artfact.getResourceURI());
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Authorization", "Basic " + this.authEncoded);
                urlConnection.setRequestProperty("Content-type", "application/xml");
                urlConnection.setRequestMethod("DELETE");
                System.out.println("Delete [" + artfact.getResourceURI() + "]:" + urlConnection.getResponseCode());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
