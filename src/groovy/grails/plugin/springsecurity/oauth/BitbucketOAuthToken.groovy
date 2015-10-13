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

import org.scribe.model.Token

/**
 * Spring security authentication token. This is used during the Grails user session.
 *
 * @author Mattias Nordvall
 */
class BitbucketOAuthToken extends OAuthToken {

    private static final String PROVIDER_NAME = 'bitbucket'
    private String displayName

    BitbucketOAuthToken(Token scribeToken, username, displayName) {
        super(scribeToken)
        this.principal = username
        this.displayName = displayName
    }

    String getSocialId() {
        return principal
    }

    String getScreenName() {
        return displayName
    }

    String getProviderName() {
        return PROVIDER_NAME
    }
}
