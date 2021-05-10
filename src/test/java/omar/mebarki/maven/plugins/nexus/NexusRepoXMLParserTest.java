package omar.mebarki.maven.plugins.nexus;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import omar.mebarki.maven.plugins.nexus.repository.NexusArtifactVersion;
import omar.mebarki.maven.plugins.nexus.repository.NexusRepoXMLParser;

import java.util.List;

import static org.junit.Assert.*;


/**
 * Created by omebarki on 22/05/2017.
 */
public class NexusRepoXMLParserTest {
    public static final String REPO_FOLDER_XML = "/repoFolder.xml";
    public static final String REPO_FOLDER_CURRUPTED_XML = "/repoFolderCurrupted.xml";
    private NexusRepoXMLParser nexusRepoXMLParser;

    @Before
    public void setUp() {
        nexusRepoXMLParser = new NexusRepoXMLParser();
    }

    @After
    public void tearDown() {
        nexusRepoXMLParser = null;
    }

    @Test
    public void should_success_when_repo_xml_is_good() {
        List<NexusArtifactVersion> nexusArtifactVersions = nexusRepoXMLParser.parse(NexusRepoXMLParserTest.class.getResourceAsStream(REPO_FOLDER_XML));
        assertEquals(5, nexusArtifactVersions.size());
    }
    @Test
    public void should_success_empty_list_when_repo_xml_is_corrupted() {
        List<NexusArtifactVersion> nexusArtifactVersions = nexusRepoXMLParser.parse(NexusRepoXMLParserTest.class.getResourceAsStream(REPO_FOLDER_CURRUPTED_XML));
        assertEquals(0, nexusArtifactVersions.size());
    }

}