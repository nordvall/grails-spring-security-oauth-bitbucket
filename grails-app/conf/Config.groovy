log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
           'org.codehaus.groovy.grails.web.pages', //  GSP
           'org.codehaus.groovy.grails.web.sitemesh', //  layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping', // URL mapping
           'org.codehaus.groovy.grails.commons', // core / classloading
           'org.codehaus.groovy.grails.plugins', // plugins
           'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'

    environments {
        test {
            // The unit tests are forcing errors which causes error messages to be logged to the console
            // We disable them during testing to avoid confusion
            off "grails.app.services.grails.plugin.springsecurity.oauth.BitbucketSpringSecurityOAuthService"
        }
    }
}

// just for test, to avoid oauthService BeanCreationException "Missing oauth secret or key (or both!) in configuration for bitbucket"
environments {
    test {
        oauth {
            providers {
                bitbucket {
                    api = se.mnord.scribe.builder.api.BitbucketApi20
                    key = 'dummy_key'
                    secret = 'dummy_secret'
                    successUri = '/oauth/bitbucket/success'
                    failureUri = '/oauth/bitbucket/failure'
                    callback = "${baseURL}/oauth/bitbucket/callback"
                }
            }
        }
    }
}