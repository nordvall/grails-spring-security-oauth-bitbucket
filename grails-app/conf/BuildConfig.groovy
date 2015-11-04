grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

grails.project.dependency.resolver = "maven"
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn"
    repositories {
        grailsCentral()
        mavenLocal()
        mavenCentral()
    }
    dependencies {
        compile('se.mnord.scribe:scribe-java-bitbucket:1.0.0')
    }

    plugins {
        compile(':spring-security-oauth:2.1.0-RC4')

        // TODO we need this to compile in Grails 2.4...
        compile ':hibernate:3.6.10.14', {
            export = false
        }

        build(":release:3.0.1",
              ":rest-client-builder:1.0.3") {
            export = false
        }
    }
}
