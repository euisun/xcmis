_This document refers to 1.1.x and 1.2.x version of xCMIS._


# Introduction #

This article explaining how to use xCMIS TCK for checking SP specification conformity using Maven or directly bare java prompt.


# Details #



## Using Maven ##

_Please, make sure you're using junit version 4.8.1 or higher, and maven-surefire-plugin version 2.5 or higher  before trying to run tests._
Connecting xCMIS-TCK via Maven is quite simple. The following four steps needed to run TCK on SP you want to test, assuming that you have already installed xcmis-spi-tck-1.2.0-SNAPSHOT-tests.jar into repository (built from sources or installed manually):

1). Add TCK profile to your SP pom.xml like following:
```
   <project>
    ...
    <profiles>
     ...    
      <profile>
         <id>tck</id>
         <activation>
            <activeByDefault>false</activeByDefault>
         </activation>
         <build>
            <plugins>
               <plugin>
                  <groupId>org.apache.maven.plugins</groupId>
                  <artifactId>maven-dependency-plugin</artifactId>
                  <executions>
                     <execution>
                        <id>unpack</id>
                        <phase>process-test-classes</phase>
                        <goals>
                           <goal>unpack</goal>
                        </goals>
                        <configuration>
                           <artifactItems>
                              <artifactItem>
                                 <groupId>org.xcmis</groupId>
                                 <artifactId>xcmis-spi-tck</artifactId>
                                 <version>1.2-SNAPSHOT</version>
                                 <type>test-jar</type>
                                 <outputDirectory>${project.build.directory}/test-classes/
                           </outputDirectory>
                              </artifactItem>
                           </artifactItems>
                          <excludes>**/TCKRunner.class</excludes>
                        </configuration>
                     </execution>
                  </executions>
               </plugin>
               <plugin>
                  <groupId>org.apache.maven.plugins</groupId>
                  <artifactId>maven-surefire-plugin</artifactId>
                  <version>2.5</version>
                  <configuration>
                     <systemPropertyVariables>
                        <org.xcmis.CmisRegistryFactory>org.your.sp.YourRegistryFactory 
                        </org.xcmis.CmisRegistryFactory>
                     </systemPropertyVariables>
                     <includes>
                        <include>**/AllTests.class</include>
                     </includes>
                  </configuration>
               </plugin>
            </plugins>
         </build>
      </profile>
```

2). Add necessary logger dependencies, e.g. for slf4j :
```
<dependency>
   <groupId>org.slf4j</groupId>
   <artifactId>slf4j-api</artifactId>
   <version>1.5.8</version>
   <scope>test</scope>
  </dependency>
  <dependency>
   <groupId>org.slf4j</groupId>
   <artifactId>slf4j-simple</artifactId>
   <version>1.5.8</version>
   <scope>test</scope>
  </dependency>
```

3). Write an factory class, which has public getRegistry() method, returning an instance of CmisRegistry with an storage provider instance contained in it, e.g:
```
public class YourRegistryFactory implements CmisRegistryFactory
CmisRegistry reg = new CmisRegistry();

  public YourRegistryFactory ()
   {
     reg.addStorage(new org.yourname.YourStorageProviderImpl("cmis1");
   }
  public CmisRegistry getRegistry()
   {
     return reg;
   }
```

4). Specify the org.xcmis.CmisRegistryFactory property with the value equal of FQN of class created in step 3 into TCK profile surefire-plugin configuration:

```
     <systemPropertyVariables>
       <org.xcmis.CmisRegistryFactory>org.your.sp.YourRegistryFactory
                        </org.xcmis.CmisRegistryFactory>
     </systemPropertyVariables>

```

So when these steps done, executing TCK tests set command on your SP is:
```
mvn test -Ptck
```
or
```
mvn clean install -Ptck
```


## Using bare java ##

1). Build TCK test-jar;

2). Executing:
```
 java -cp your-sp.jar [your-sp-dependency1.jar your-sp-dependency2.jar ...]
 -Dorg.xcmis.CmisRegistryFactory=org.your.sp.YourRegistryFactory
 org.xcmis.spi.tck.TCKRunner 
```