package omar.mebarki.maven.plugins.nexus.filter;

import omar.mebarki.maven.plugins.nexus.repository.CleanUpOrder;

import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Created by omebarki on 23/05/2017.
 */
public class FilterFactory {
    public static NexusArtifactsFilter createFilter(int keepMin, int olderThanMonths, ZonedDateTime now, String versionToClean, String thresholdVersion, CleanUpOrder order) {
        if (Objects.nonNull(thresholdVersion)) {
            if (CleanUpOrder.DATE.equals(order)) {
                return new NexusArtifactsVersionThresholdFilter(thresholdVersion);
            } else {
                return new NexusArtifactsVersionThresholdNumFilter(thresholdVersion);
            }

        } else if (Objects.nonNull(versionToClean)) {
            return new NexusArtifactsVersionFilter(versionToClean);
        } else {
            return new NexusArtifactsDateFilter(keepMin, olderThanMonths, now);
        }
    }
}
