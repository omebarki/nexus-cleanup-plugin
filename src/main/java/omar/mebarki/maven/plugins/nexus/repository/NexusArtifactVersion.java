package omar.mebarki.maven.plugins.nexus.repository;

import java.time.ZonedDateTime;

/**
 * Created by omebarki on 19/05/2017.
 */
public class NexusArtifactVersion implements Comparable<NexusArtifactVersion> {
    private final String resourceURI;
    private final ZonedDateTime lastModified;
    private final String text;

    public NexusArtifactVersion(String resourceURI, ZonedDateTime lastModified, String text) {
        this.resourceURI = resourceURI;
        this.lastModified = lastModified;
        this.text = text;
    }

    public String getResourceURI() {
        return resourceURI;
    }

    public ZonedDateTime getLastModified() {
        return lastModified;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "NexusArtifactVersion{" +
                "resourceURI='" + resourceURI + '\'' +
                ", lastModified=" + lastModified +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NexusArtifactVersion that = (NexusArtifactVersion) o;

        if (!resourceURI.equals(that.resourceURI)) return false;
        if (!lastModified.equals(that.lastModified)) return false;
        return text.equals(that.text);
    }

    @Override
    public int hashCode() {
        int result = resourceURI.hashCode();
        result = 31 * result + lastModified.hashCode();
        result = 31 * result + text.hashCode();
        return result;
    }

    @Override
    public int compareTo(NexusArtifactVersion o) {
        return this.lastModified.compareTo(o.lastModified);
    }
}
