# nexus-cleanup-plugin
# Usage
##1. Delete a specific version

```bash
    mvn omar.mebarki.maven.plugins:nexus-cleanup-plugin:cleanup -DnexusServerId=omar.mebarki.com -DversionToClean=1.0.0
```
In this case, the plugin will use **distributionManagement** information of the project to get the nexus repository URL.
  And the server information on **settings.xml** of your maven the get the credentials of nexusServerId
  
Or if you want to specify the nexus URL and the credentials manually 
   
```bash
   mvn omar.mebarki.maven.plugins:nexus-cleanup-plugin:cleanup \
   -DnexusURL=https://omar.mebarki.com/nexus-om/service/local/repositories/my-repo-maven2-hosted-releases/content/ \
   -DnexusUserName=<username> -DnexusPassword=<password> \
   -DversionToClean=1.0.0
```
   
##2. Delete a set of versions based on their age

```bash
    mvn omar.mebarki.maven.plugins:nexus-cleanup-plugin:cleanup -DnexusServerId=omar.mebarki.com \
    -DkeepMin=10 -DolderThanMonths=5
```
This will keep at least the 10 latest versions and delete all the other versions that are older than 5 moths.

You can omit the parameters *keepMin* and *olderThanMonths*
By default **keepMin** is equal to **12** and **olderThanMonths** to **6**
```bash
    mvn omar.mebarki.maven.plugins:nexus-cleanup-plugin:cleanup -DnexusServerId=omar.mebarki.com
```

##3. Delete versions older than a specific version

```bash
    mvn omar.mebarki.maven.plugins:nexus-cleanup-plugin:cleanup -DnexusServerId=omar.mebarki.com \
    -DthresholdVersion=17.2.1
```
This will delete all version older than *17.2.1* using the numbers as comparison criteria.

If you want to delete older version using *date* as comparison criteria
```bash
    mvn omar.mebarki.maven.plugins:nexus-cleanup-plugin:cleanup -DnexusServerId=omar.mebarki.com \
    -DthresholdVersion=17.2.1 -Dorder=DATE
```

# Maven 
- To release, execute :
  ```bash
   mvn  -B release:prepare release:perform 
   ```
   
