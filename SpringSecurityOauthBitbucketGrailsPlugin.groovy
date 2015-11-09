/*
   Copyright 2015 Mattias Nordvall

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

import grails.util.Environment


class SpringSecurityOauthBitbucketGrailsPlugin {
    def version = "1.0.1"
    def grailsVersion = "2.0 > *"

    def loadAfter = ['springSecurityOauth']

    def title = "Bitbucket for Spring Security Oauth Plugin"
    def author = "Mattias Nordvall"
    def authorEmail = "mattias@mnord.se"
    def description = '''\
Integrate [Bitbucket|http://bitbucket.org] to [Spring Security OAuth plugin|http://grails.org/plugin/spring-security-oauth].
'''

    def documentation = "http://github.com/nordvall/spring-security-oauth-bitbucket"

    def license = "APACHE"

    def issueManagement = [system: 'GITHUB',
                           url   : 'https://github.com/nordvall/grails-spring-security-oauth-bitbucket/issues']
    def scm = [url: 'https://github.com/nordvall/grails-spring-security-oauth-bitbucket']

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
