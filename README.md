# grails-spring-security-oauth-bitbucket

This plugin adds [Bitbucket](https://bitbucket.org) support to the [Grails Spring Security OAuth](http://grails.org/plugin/spring-security-oauth) plugin.
It makes it possible for your users to associate their Bitbucket logins to their Grails accounts.

## Usage
Follow these instruction to install and configure the plugin.

### BuildConfig.groovy
If you are using standard Grails dependency resolution, add this dependency to your BuildConfig.groovy.

```groovy
plugins {
        compile ':spring-security-oauth-bitbucket:1.0.1'
}
```

### Maven pom.xml
If you are using Maven based Grails project, add the dependencies in pom.xml instead:

```xml
    <dependency>
        <groupId>org.grails.plugins</groupId>
        <artifactId>spring-security-oauth-bitbucket</artifactId>
        <version>1.0.1</version>
        <scope>compile</scope>
        <type>zip</type>
    </dependency>
```

### Register application in Bitbucket
You need to register your application in Bitbucket. Currently it is done under "Manage account" -> OAuth -> Add consumer.

* Name: Name of your application. Does not need to match anything in the config, but it will be shown too your users on theit first login.
* Callback URL: Needs to match "callback" in the config below.
* Permissions: You need "Email" and "Read" under "Account" to be able to login users. More permissions will be needed if you plan to call other Bitbucket API:s.


### Config.groovy
When you have registered your application in Bitbucket, then add these settings to your Config.groovy:

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

### Login link
To create a login link for Bitbucket, you could use the "connect" tag from the "oauth" taglib:

```xml
<oauth:connect provider="bitbucket">Login with Bitbucket</oauth:connect>
```

### Further configuration
For more configuration instructions, see http://grails.org/plugin/spring-security-oauth

