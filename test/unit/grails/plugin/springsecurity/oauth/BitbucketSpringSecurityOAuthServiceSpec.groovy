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

import grails.test.mixin.TestFor
import org.scribe.model.Token
import spock.lang.Specification

@TestFor(BitbucketSpringSecurityOAuthService)
class BitbucketSpringSecurityOAuthServiceSpec extends Specification {

    BitbucketSpringSecurityOAuthService service

    def setup() {
        service = new BitbucketSpringSecurityOAuthService()
        service.oauthService = [:]
    }

    def "Successful API response should return correct user details"() {
        given:
        def oauthAccessToken = new Token('token', 'secret', 'rawResponse')

        service.oauthService.getBitbucketResource = { accessToken, url ->
            assert accessToken == oauthAccessToken
            return [code: 200, body:'{ "display_name": "Grandma", "username": "granny" }']
        }

        when:
        BitbucketOAuthToken token = service.createAuthToken(oauthAccessToken)

        then:
        token.principal == 'granny'
        token.screenName == "Grandma"
        token.providerName == 'bitbucket'
    }



    def "Unsuccessful API response should throw exception"() {
        given:
        def oauthAccessToken = new Token('token', 'secret', 'rawResponse')

        service.oauthService.getBitbucketResource = { accessToken, url ->
            assert accessToken == oauthAccessToken
            return [code: 500, body:'{"error": {"message": "Error"}}']
        }

        when:
        service.createAuthToken(oauthAccessToken)

        then:
        OAuthLoginException ex = thrown()
        ex.message.contains("500")
    }



    def "Empty API response body throw exception"() {
        given:
        def oauthAccessToken = new Token('token', 'secret', 'rawResponse')

        service.oauthService.getBitbucketResource = { accessToken, url ->
            assert accessToken == oauthAccessToken
            return [code: 200, body:'']
        }

        when:
        service.createAuthToken(oauthAccessToken)

        then:
        thrown(OAuthLoginException)
    }

    def "Missing username property should throw exception"() {
        given:
        def oauthAccessToken = new Token('token', 'secret', 'rawResponse')

        service.oauthService.getBitbucketResource = { accessToken, url ->
            assert accessToken == oauthAccessToken
            return [code: 200, body:'{ "display_name": "Grandma"}']
        }

        when:
        service.createAuthToken(oauthAccessToken)

        then:
        OAuthLoginException ex = thrown()
        ex.message.contains("username")
    }

    def "Missing display_name property should throw exception"() {
        given:
        def oauthAccessToken = new Token('token', 'secret', 'rawResponse')

        service.oauthService.getBitbucketResource = { accessToken, url ->
            assert accessToken == oauthAccessToken
            return [code: 200, body:'{ "username": "granny"}']
        }

        when:
        service.createAuthToken(oauthAccessToken)

        then:
        OAuthLoginException ex = thrown()
        ex.message.contains("display_name")
    }
}
