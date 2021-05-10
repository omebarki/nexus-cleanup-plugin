package omar.mebarki.maven.plugins.nexus.filter;

import omar.mebarki.maven.plugins.nexus.repository.NexusArtifactVersion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by omebarki on 22/05/2017.
 */
public class NexusArtifactsVersionFilter implements NexusArtifactsFilter {


    private final String version;

    public NexusArtifactsVersionFilter(String version) {
        this.version = version;
    }

    @Override
    public List<NexusArtifactVersion> filter(final List<NexusArtifactVersion> artifactsVersions) {
        List<NexusArtifactVersion> filtered = new ArrayList<>(artifactsVersions);
        filtered.removeIf(artifact -> !this.version.equals(artifact.getText()));
        return filtered;
    }
}
