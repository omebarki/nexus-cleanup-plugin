package omar.mebarki.maven.plugins.nexus;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import omar.mebarki.maven.plugins.nexus.filter.NexusArtifactsFilter;
import omar.mebarki.maven.plugins.nexus.filter.NexusArtifactsVersionThresholdFilter;
import omar.mebarki.maven.plugins.nexus.repository.NexusArtifactVersion;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by omebarki on 22/05/2017.
 */
public class NexusThresholdArtifactsFilterTest {
    private List<NexusArtifactVersion> artifactsVersions;

    @Before
    public void setUp() {
        artifactsVersions = new ArrayList<>();
        artifactsVersions.add(new NexusArtifactVersion("uri",
                ZonedDateTime.parse("2017-04-05 13:49:46.0 UTC",
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S z")), "text6")
        );
        artifactsVersions.add(new NexusArtifactVersion("uri",
                ZonedDateTime.parse("2017-03-05 13:49:46.0 UTC",
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S z")), "text5")
        );
        artifactsVersions.add(new NexusArtifactVersion("uri",
                ZonedDateTime.parse("2017-05-05 13:49:46.0 UTC",
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S z")), "text7")
        );
        artifactsVersions.add(new NexusArtifactVersion("uri",
                ZonedDateTime.parse("2016-05-05 13:49:46.0 UTC",
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S z")), "text1")
        );
        artifactsVersions.add(new NexusArtifactVersion("uri",
                ZonedDateTime.parse("2016-08-05 13:49:46.0 UTC",
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S z")), "text2")
        );
        artifactsVersions.add(new NexusArtifactVersion("uri",
                ZonedDateTime.parse("2017-01-05 13:49:46.0 UTC",
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S z")), "text2")
        );
        artifactsVersions.add(new NexusArtifactVersion("uri",
                ZonedDateTime.parse("2017-02-05 13:49:46.0 UTC",
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S z")), "text4")
        );
    }

    @After
    public void tearDown() {
        artifactsVersions = null;
    }

    @Test
    public void filter_by_threshold() throws Exception {
        NexusArtifactsFilter nexusArtifactsFilter = new NexusArtifactsVersionThresholdFilter("text5");
        List<NexusArtifactVersion> toDelete = nexusArtifactsFilter.filter(artifactsVersions);
        assertEquals(4, toDelete.size());

    }

}