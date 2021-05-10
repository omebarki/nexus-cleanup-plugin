package omar.mebarki.maven.plugins.nexus.filter;

import omar.mebarki.maven.plugins.nexus.repository.NexusArtifactVersion;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by omebarki on 22/05/2017.
 */
public class NexusArtifactsDateFilter implements NexusArtifactsFilter {
    private final int keepMin;

    private final int olderThanMonths;
    private final ZonedDateTime now;

    public NexusArtifactsDateFilter(int keepMin, int olderThanMonths, ZonedDateTime now) {
        this.now = now;
        if (keepMin < 0) {
            throw new IllegalArgumentException("keepMin should be >0");
        }
        if (olderThanMonths < 0) {
            throw new IllegalArgumentException("keepMin olderThanMonths be >0");
        }
        this.keepMin = keepMin;
        this.olderThanMonths = olderThanMonths;
    }

    @Override
    public List<NexusArtifactVersion> filter(final List<NexusArtifactVersion> artifactsVersions) {
        List<NexusArtifactVersion> filtered = artifactsVersions.stream().sorted((nav1, nav2) -> nav1.getLastModified().compareTo(nav2.getLastModified()))
                .collect(Collectors.toList());
        //keep keepMin
        int keepMinCounter = keepMin;
        while (keepMinCounter > 0 && !filtered.isEmpty()) {
            keepMinCounter--;
            filtered.remove(filtered.size() - 1);
        }

        //months
        filtered.removeIf(artifact -> {
            ZonedDateTime before = now.minusMonths(this.olderThanMonths);
            return artifact.getLastModified().isAfter(before);
        });
        return filtered;
    }
}
