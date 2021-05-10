package omar.mebarki.maven.plugins;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.settings.Server;
import org.apache.maven.settings.Settings;
import omar.mebarki.maven.plugins.nexus.filter.FilterFactory;
import omar.mebarki.maven.plugins.nexus.filter.NexusArtifactsFilter;
import omar.mebarki.maven.plugins.nexus.repository.CleanUpOrder;
import omar.mebarki.maven.plugins.nexus.repository.NexusRepoXMLParser;
import omar.mebarki.maven.plugins.nexus.repository.NexusRepository;
import omar.mebarki.maven.plugins.nexus.repository.URLSUtils;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * Goal which touches a timestamp file.
 *
 * @goal touch
 * @phase process-sources
 */
@Mojo(name = "cleanup")
public class NexusCleanUpMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject project;

    @Parameter(readonly = true, required = true, property = "settings")
    private Settings settings;

    @Parameter(property = "nexusServerId")
    private String nexusServerId;

    @Parameter(property = "nexusURL")
    private String nexusURL;

    @Parameter(property = "nexusUserName")
    private String nexusUserName;

    @Parameter(property = "nexusPassword")
    private String nexusPassword;

    @Parameter(property = "keepMin", defaultValue = "12")
    private int keepMin;

    @Parameter(property = "olderThanMonths", defaultValue = "6")
    private int olderThanMonths;

    @Parameter(property = "versionToClean")
    private String versionToClean;

    @Parameter(property = "thresholdVersion")
    private String thresholdVersion;

    @Parameter(property = "order", defaultValue = "VERSION")
    private CleanUpOrder order;


    private Optional<String> nexusSnapshotURL = Optional.empty();

    public void execute() throws MojoExecutionException {
        populateCredentialsAndURLS();
        removeArtifacts(nexusURL);
        nexusSnapshotURL.ifPresent(this::removeArtifacts);
    }

    private void removeArtifacts(String url) {
        try {
            NexusRepository nexusRepository = bootStrap(url);
            nexusRepository.cleanUP(getArtifactPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void populateCredentialsAndURLS() {
        if (Objects.nonNull(nexusServerId)) {
            Server server = settings.getServer(nexusServerId);
            nexusUserName = server.getUsername();
            nexusPassword = server.getPassword();
        }
        if (Objects.isNull(nexusURL)) {
            nexusURL = project.getDistributionManagement().getRepository().getUrl();
            nexusURL = URLSUtils.convertToContentAPI(nexusURL);
            nexusSnapshotURL = Optional.of(URLSUtils.convertToContentAPI(project.getDistributionManagement().getSnapshotRepository().getUrl()));
        }
    }

    private NexusRepository bootStrap(String url) {
        NexusRepoXMLParser nexusRepoXMLParser = new NexusRepoXMLParser();
        NexusArtifactsFilter nexusArtifactsFilter = FilterFactory.createFilter(keepMin, olderThanMonths, ZonedDateTime.now(), versionToClean, thresholdVersion, order);
        NexusRepository nexusRepository = new NexusRepository(url, nexusUserName, nexusPassword, nexusRepoXMLParser, nexusArtifactsFilter);
        return nexusRepository;
    }

    private String getArtifactPath() {
        return project.getGroupId().replace('.', '/') + "/" + project.getArtifactId();
    }
}
