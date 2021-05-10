package omar.mebarki.maven.plugins.nexus.filter;

import omar.mebarki.maven.plugins.nexus.repository.NexusArtifactVersion;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by omebarki on 22/05/2017.
 */
public class NexusArtifactsVersionThresholdFilter implements NexusArtifactsFilter {


    private final String thresholdVersion;

    public NexusArtifactsVersionThresholdFilter(String thresholdVersion) {
        this.thresholdVersion = thresholdVersion;
    }

    @Override
    public List<NexusArtifactVersion> filter(final List<NexusArtifactVersion> artifactsVersions) {
        List<NexusArtifactVersion> filtered = artifactsVersions.stream().sorted((nav1, nav2) -> nav1.getLastModified().compareTo(nav2.getLastModified()))
                .collect(Collectors.toList());
        List<NexusArtifactVersion> currentVersionList = filtered.stream().filter(
                artifact -> artifact.getText().equals(this.thresholdVersion)).collect(Collectors.toList());
        if (currentVersionList.isEmpty()) {
            filtered = new ArrayList<>();
        } else {
            NexusArtifactVersion currentVersion = currentVersionList.get(0);
            int indexOfCurrentVersion = filtered.indexOf(currentVersion);
            filtered = filtered.subList(0, indexOfCurrentVersion);
        }
        return filtered;
    }
}
