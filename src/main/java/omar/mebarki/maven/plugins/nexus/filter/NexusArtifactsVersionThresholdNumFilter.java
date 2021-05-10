package omar.mebarki.maven.plugins.nexus.filter;

import omar.mebarki.maven.plugins.nexus.repository.NexusArtifactVersion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by omebarki on 22/05/2017.
 */
public class NexusArtifactsVersionThresholdNumFilter implements NexusArtifactsFilter {


    private final String thresholdVersion;
    private int majorVersion;
    private int minorVersion;
    private int incrementalVersion;


    public NexusArtifactsVersionThresholdNumFilter(String thresholdVersion) {
        this.thresholdVersion = thresholdVersion;
        String[] split = thresholdVersion.split("\\.");
        majorVersion = Integer.valueOf(split[0]);
        minorVersion = Integer.valueOf(split[1]);
        incrementalVersion = Integer.valueOf(split[2]);
    }

    @Override
    public List<NexusArtifactVersion> filter(final List<NexusArtifactVersion> artifactsVersions) {
        List<NexusArtifactVersion> filtered = new ArrayList<>(artifactsVersions);
        filtered.removeIf(this::canRemove);
        return filtered;
    }

    private boolean canRemove(NexusArtifactVersion artifactVersion) {
        try {
            String[] split = artifactVersion.getText().split("\\.");
            int artifactMajorVersion = Integer.valueOf(split[0]);
            int artifactMinorVersion = Integer.valueOf(split[1]);
            int artifactIncrementalVersion = Integer.valueOf(split[2]);
            int c = Integer.compare(majorVersion, artifactMajorVersion);
            if (c == 0) {
                c = Integer.compare(minorVersion, artifactMinorVersion);
            }
            if (c == 0) {
                c = Integer.compare(incrementalVersion, artifactIncrementalVersion);
            }
            return c <= 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
