import grails.util.Holders


def appName = grails.util.Metadata.current.'app.name'
def baseURL = Holders.config.grails.serverURL ?: "http://localhost:${System.getProperty('server.port', '8080')}/${appName}"

oauth {
    providers {
        bitbucket {
            api = org.scribe.builder.api.Bitbucket20Api
            successUri = '/oauth/bitbucket/success'
            failureUri = '/oauth/bitbucket/failure'
            callback = "${baseURL}/oauth/bitbucket/callback"
        }
    }
}

