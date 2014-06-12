//grails.plugin.location.Restrpc='../grails-api-toolkit'
grails.servlet.version = "3.0" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target/work"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
grails.project.war.file = "target/${appName}-${appVersion}.war"
grails.tomcat.nio=true
grails.tomcat.jvmArgs = ["-Xms2048m", "-Xmx4096m", "-XX:PermSize=1024m", "-XX:MaxPermSize=2048m", "-XX:+CMSClassUnloadingEnabled", "-XX:+UseConcMarkSweepGC"]

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsHome()
        mavenLocal()
        grailsCentral()
        mavenCentral()
        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        //mavenRepo "http://repository.codehaus.org"
        mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
		mavenRepo "http://repo.spring.io/milestone/"
    }

    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes e.g.
        runtime 'mysql:mysql-connector-java:5.1.24'
		test "org.spockframework:spock-grails-support:0.7-groovy-2.0"
    }

    plugins {
        // plugins for the build system only
        build ':tomcat:latest.release'

        // plugins for the compile step
        //compile ":scaffolding:2.0.1"
        compile ':cache:latest.release'


        // plugins needed at runtime but not for compilation
        compile ":hibernate:3.6.10.7"
        runtime ":database-migration:1.3.8"
        runtime ":jquery:1.10.2.2"
        //runtime ":resources:1.2.1"

        // Uncomment these (or add new ones) to enable additional resources capabilities
        //runtime ":zipped-resources:1.0.1"
        //runtime ":cached-resources:1.1"
        //runtime ":yui-minify-resources:0.1.5"
	compile ":spring-security-core:2.0-RC3"
	// compile ":spring-security-ui:1.0-RC1"
	compile ":famfamfam:1.0"
	compile ":jquery-ui:1.10.3"
	compile ":mail:1.0"
	compile ":api-toolkit:2.0.9"

	// used for testing
	test(":spock:0.7") {
		export=false
		exclude "spock-grails-support"
	}
	compile ":rest:0.8"
    }
}
