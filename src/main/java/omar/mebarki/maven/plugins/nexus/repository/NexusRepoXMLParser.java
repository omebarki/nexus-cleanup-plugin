package omar.mebarki.maven.plugins.nexus.repository;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by omebarki on 19/05/2017.
 */
public class NexusRepoXMLParser {
    public List<NexusArtifactVersion> parse(InputStream xmlContent) {
        List<NexusArtifactVersion> nexusArtifactsVersions = new ArrayList<>();
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(xmlContent);
            NodeList contentItems = document.getElementsByTagName("content-item");
            for (int i = 0; i < contentItems.getLength(); i++) {
                addNewNexusArtifactVersion(nexusArtifactsVersions, contentItems.item(i));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        Collections.sort(nexusArtifactsVersions);
        return nexusArtifactsVersions;
    }

    private void addNewNexusArtifactVersion(List<NexusArtifactVersion> nexusArtifactVersions, Node contentItem) {
        NodeList childNodes = contentItem.getChildNodes();
        String resourceURI = null;
        String text = null;
        ZonedDateTime lastModified = null;
        String relativePath = null;
        boolean isLeaf = false;
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            String nodeName = item.getNodeName();
            String nodeValue = item.getTextContent();
            switch (nodeName) {
                case "resourceURI": {
                    resourceURI = nodeValue;
                }
                break;
                case "text": {
                    text = nodeValue;
                }
                break;
                case "lastModified": {
                    lastModified = ZonedDateTime.parse(nodeValue,
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S z"));
                }
                break;
                case "leaf": {
                    isLeaf = "leaf".equalsIgnoreCase("true");
                }
                break;
                case "relativePath": {
                    relativePath = nodeValue;
                }
                break;
            }
        }
        if (!isLeaf && relativePath.endsWith("/")) {
            nexusArtifactVersions.add(new NexusArtifactVersion(resourceURI, lastModified, text));
        }
    }
}
