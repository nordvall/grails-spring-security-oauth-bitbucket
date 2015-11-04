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

package grails.plugin.springsecurity.oauth

import grails.converters.JSON
import org.codehaus.groovy.grails.web.converters.exceptions.ConverterException
import org.scribe.model.Token

/**
 * Grails service to build Spring Security tokens from Bitbucket authentication
 *
 * @author Mattias Nordvall
 */
class BitbucketSpringSecurityOAuthService {
    private static final String userApiEndpoint = "https://api.bitbucket.org/2.0/user"
    def oauthService // in runtime this is uk.co.desirableobjects.oauth.scribe.OauthService

    /**
     * Queries the Bitbucket user api for additional user details,
     * then builds a Spring Security authentication token
     *
     * Called by @{link grails.plugin.springsecurity.oauth.SpringSecurityOAuthService}
     * using "getBean("${providerName}SpringSecurityOAuthService")"
     *
     * @param accessToken   A Scribe {@link org.scribe.model.Token},
     *                      obtained from {@link se.mnord.scribe.oauth.BitbucketService20}
     * @return              A Spring Security authentication token:
     *                      {@link grails.plugin.springsecurity.oauth.BitbucketOAuthToken}
     */
    def createAuthToken(Token accessToken) {
        log.info("Querying Bitbucket user api endpoint: ${userApiEndpoint}")

        // This is targeting the "methodMissing" method of the oauthService class...
        def response = oauthService.getBitbucketResource(accessToken, userApiEndpoint)
        log.debug("Api response: ${response.body}")

        try {
            assert response.code == 200 : "HTTP Error code from Bitbucket api: ${response.code}"

            def bitbucketUser = JSON.parse(response.body)
            assert bitbucketUser.username : "username missing from api response"
            assert bitbucketUser.display_name : "display_name missing from api response"

            log.debug("Creating Spring Security token from Scribe token...")
            return new BitbucketOAuthToken(accessToken, bitbucketUser.username, bitbucketUser.display_name)
        } catch (ConverterException | AssertionError ex) {
            log.error "Error parsing response from Bitbucket: ${response?.body}"
            throw new OAuthLoginException("Error parsing response from Bitbucket: ${ex.message}", ex)
        }
    }
}
