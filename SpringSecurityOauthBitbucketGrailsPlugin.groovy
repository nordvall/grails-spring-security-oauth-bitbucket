import grails.util.Environment


class SpringSecurityOauthBitbucketGrailsPlugin {
    def version = "1.0"
    def grailsVersion = "2.0 > *"

    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    def loadAfter = ['springSecurityOauth']

    def title = "Bitbucket for Spring Security Oauth Plugin"
    def author = "Mattias Nordvall"
    def authorEmail = ""
    def description = '''\
Integrate [Bitbucket|http://bitbucket.org] to [Spring Security OAuth plugin|http://grails.org/plugin/spring-security-oauth].
'''

    def documentation = "http://grails.org/plugin/spring-security-oauth-bitbucket"

    def license = "APACHE"

    // Based on OAuth plugins made by these people:
    def developers = [
            [name: 'Mihai Cazacu', email: 'cazacugmihai@gmail.com'],
            [name: 'Enrico Comiti', email: 'enrico@comiti.name'],
            [name: 'Alexey Zhokhov', email: 'donbeave@gmail.com']
    ]

    def doWithSpring = {
        def classLoader = new GroovyClassLoader(getClass().classLoader)

        // Load default config as a basis
        def newConfig = new ConfigSlurper(Environment.current.name).parse(
                classLoader.loadClass('DefaultBitbucketOauthConfig')
        )

        newConfig.oauth.providers.bitbucket.merge(application.config.oauth.providers.bitbucket)
        application.config.merge(newConfig)
    }
}
