package omar.mebarki.maven.plugins.nexus;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import omar.mebarki.maven.plugins.nexus.filter.NexusArtifactsDateFilter;
import omar.mebarki.maven.plugins.nexus.repository.NexusArtifactVersion;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by omebarki on 22/05/2017.
 */
public class NexusArtifactsFilterTest {
    private List<NexusArtifactVersion> artifactsVersions;

    @Before
    public void setUp() {
        artifactsVersions = new ArrayList<>();
        artifactsVersions.add(new NexusArtifactVersion("uri",
                ZonedDateTime.parse("2017-02-05 13:49:46.0 UTC",
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S z")), "text")
        );
        artifactsVersions.add(new NexusArtifactVersion("uri",
                ZonedDateTime.parse("2017-03-05 13:49:46.0 UTC",
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S z")), "text")
        );
        artifactsVersions.add(new NexusArtifactVersion("uri",
                ZonedDateTime.parse("2017-04-05 13:49:46.0 UTC",
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S z")), "text")
        );
        artifactsVersions.add(new NexusArtifactVersion("uri",
                ZonedDateTime.parse("2017-05-05 13:49:46.0 UTC",
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S z")), "text")
        );
        artifactsVersions.add(new NexusArtifactVersion("uri",
                ZonedDateTime.parse("2016-05-05 13:49:46.0 UTC",
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S z")), "text")
        );
        artifactsVersions.add(new NexusArtifactVersion("uri",
                ZonedDateTime.parse("2016-08-05 13:49:46.0 UTC",
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S z")), "text")
        );
        artifactsVersions.add(new NexusArtifactVersion("uri",
                ZonedDateTime.parse("2017-01-05 13:49:46.0 UTC",
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S z")), "text")
        );

    }

    @After
    public void tearDown() {
        artifactsVersions = null;
    }

    @Test
    public void filter() throws Exception {
        NexusArtifactsDateFilter nexusArtifactsFilter = new NexusArtifactsDateFilter(2, 2, ZonedDateTime.parse("2017-05-22 13:49:46.0 UTC",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S z")));
        List<NexusArtifactVersion> toDelete = nexusArtifactsFilter.filter(artifactsVersions);
        assertEquals(5, toDelete.size());

        nexusArtifactsFilter = nexusArtifactsFilter = new NexusArtifactsDateFilter(2, 6, ZonedDateTime.parse("2017-05-22 13:49:46.0 UTC",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S z")));
        toDelete = nexusArtifactsFilter.filter(artifactsVersions);
        assertEquals(2, toDelete.size());

    }

}