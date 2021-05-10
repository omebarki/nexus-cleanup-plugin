package omar.mebarki.maven.plugins.nexus.filter;

import omar.mebarki.maven.plugins.nexus.repository.NexusArtifactVersion;

import java.util.List;

/**
 * Created by omebarki on 23/05/2017.
 */
public interface NexusArtifactsFilter {
    List<NexusArtifactVersion> filter(final List<NexusArtifactVersion> artifactsVersions);
}
