# spring-security-oauth-bitbucket

This plugin adds Bitbucket support to the [Grails Spring Security OAuth](https://github.com/enr/grails-spring-security-oauth) plugin.

## Use the plugin
To make the plugin avaiable to your Grails projects, clone this repo and run "grails maven-install" from the root folder. 
That installs the plugin in your local Maven cache.

In your Grails project, add the plugin and the major OAuth support plugin as dependencies:

### BuildConfig.groovy
If you are using standard Grails dependency resolution, add these dependencies to your BuildConfig.groovy.

```groovy
plugins {
        compile ':spring-security-oauth:2.1.0-SNAPSHOT'
        compile ':spring-security-oauth-bitbucket:1.0.0'
}
```

### Maven pom.xml
If you are using Maven based Grails project, add the dependencies in pom.xml instead:

```xml
    <dependency>
        <groupId>org.grails.plugins</groupId>
        <artifactId>spring-security-oauth</artifactId>
        <version>2.1.0-RC4</version>
        <scope>compile</scope>
        <type>zip</type>
    </dependency>
    <dependency>
        <groupId>org.grails.plugins</groupId>
        <artifactId>spring-security-oauth-bitbucket</artifactId>
        <version>1.0</version>
        <scope>compile</scope>
        <type>zip</type>
    </dependency>
```

### Config.groovy
Register your application in Bitbucket, then add the settings to Grails Config.groovy

```groovy
oauth {
    providers {
        bitbucket {
            callback = 'http://servername/oauth/bitbucket/callback'
            key = 'key_provided_by_bitbucket'
            secret = 'secret_provided_by_bitbucket'
        }
    }
}
```

### Further configuration
For more configuration instructions, see https://github.com/enr/grails-spring-security-oauth

