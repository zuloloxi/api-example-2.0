// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }
import org.apache.log4j.*

grails.config.locations = ["file:${userHome}/.test/test.properties"]

grails.war.dependencies = {
	fileset(dir: "libs") {
		deps.each { pattern -> include(name: pattern) }
	}
}

grails.project.groupId = "${appName}" // change this to alter the default package name and Maven publishing destination

// The ACCEPT header will not be used for content negotiation for user agents containing the following strings (defaults to the 4 major rendering engines)
grails.mime.disable.accept.header.userAgents = ['Gecko', 'WebKit', 'Presto', 'Trident']
grails.mime.types = [
    all:           '*/*',
    atom:          'application/atom+xml',
    css:           'text/css',
    csv:           'text/csv',
    form:          'application/x-www-form-urlencoded',
    html:          ['text/html','application/xhtml+xml'],
    js:            'text/javascript',
    json:          ['application/json', 'text/json'],
    multipartForm: 'multipart/form-data',
    rss:           'application/rss+xml',
    text:          'text/plain',
    hal:           ['application/hal+json','application/hal+xml'],
    xml:           ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']

// Legacy setting for codec used to encode data with ${}
grails.views.default.codec = "html"

// The default scope for controllers. May be prototype, session or singleton.
// If unspecified, controllers are prototype scoped.
grails.controllers.defaultScope = 'singleton'

// GSP settings
grails {
    views {
        gsp {
            encoding = 'UTF-8'
            htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
            codecs {
                expression = 'html' // escapes values inside ${}
                scriptlet = 'html' // escapes output from scriptlets in GSPs
                taglib = 'none' // escapes output from taglibs
                staticparts = 'none' // escapes output from static template parts
            }
        }
        // escapes all not-encoded output at final stage of outputting
        filteringCodecForContentType {
            //'text/html' = 'html'
        }
    }
}
 
grails.converters.encoding = "UTF-8"
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

environments {
    development {
	    log4j = {
	      appenders {
	        file name: 'grailsfile', file: 'target/grails.log'
	        file name: 'rootlog', file: 'target/root.log'
	        file name: 'devfile', file: 'target/development.log',
	          layout: pattern(conversionPattern: "[%d{HH:mm:ss:SSS}] %-5p %c{2}: %m%n")
	      }
		  //debug 'org.springframework.security'

	      root { error 'stdout', 'rootlog' }
	      info additivity: false, grailsfile:[
			  'org.codehaus.groovy.grails.commons',
			  'com.linkedin.grails'
			 ]
	      all additivity: false, devfile: [
			  'grails.app.controllers.net.nosegrind',
			  'grails.app.domain.net.nosegrind',
			  'grails.app.services.net.nosegrind.apitoolkit',
			  'grails.app.taglib.net.nosegrind.apitoolkit',
			  'grails.app.conf.your.package',
			  'grails.app.filters.your.package'
		  	]
	     }
		
        grails.logging.jul.usebridge = true
		grails.app.context = "/"
		grails.serverURL = "http://localhost:8080"
		
		apitoolkit.apiobjectSrc = 'src/apiObject'
    }
    production {
		log4j = {
			//trace 'org.hibernate.type'
			// debug 'org.hibernate.SQL'
			//debug 'org.springframework.security'

			root {
			    error()
				//debug 'stdout', 'file'
			    additivity = true
			}
		}
		
        grails.logging.jul.usebridge = false
		grails.app.context = "/"
		
		apitoolkit.apiobjectSrc = 'WEB-INF/classes/apiObject'
    }
}

// Added by the Api Toolkit plugin
apitoolkit.apiName = 'api'
apitoolkit.attempts = 5
apitoolkit.apichain.limit=3
apitoolkit.user.roles = ['ROLE_USER']
apitoolkit.admin.roles = ['ROLE_ROOT','ROLE_ADMIN']
apitoolkit.apiRoot = (grailsApplication.config.apitoolkit.apiName)?"${grailsApplication.config.apitoolkit.apiName}_v${grailsApplication.metadata['app.version']}":"v${grailsApplication.metadata['app.version']}"


// Added by the Api Toolkit plugin:
apitoolkit.domain = 'net.nosegrind.apitoolkit.Hook'
apitoolkit.controller = 'net.nosegrind.apitoolkit.HookController'

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'net.nosegrind.apitoolkit.Person'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'net.nosegrind.apitoolkit.PersonRole'
grails.plugin.springsecurity.authority.className = 'net.nosegrind.apitoolkit.Role'

grails.plugin.springsecurity.rememberMe.alwaysRemember = true
grails.plugin.springsecurity.rememberMe.cookieName="apiTest"
grails.plugin.springsecurity.rememberMe.key="_grails_"
//grails.plugin.springsecurity.rememberMe.persistent = true
//grails.plugin.springsecurity.rememberMe.persistentToken.domainClassName = 'net.nosegrind.apitoolkit.PersistentLogin'
//grails.plugin.springsecurity.auth.forceHttps=true

grails.plugin.springsecurity.logout.postOnly = false
grails.plugin.springsecurity.ui.encodePassword = false

//grails.plugin.springsecurity.fii.rejectPublicInvocations = false
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
	'/**':             ['IS_AUTHENTICATED_ANONYMOUSLY'],
	'/':                              ['permitAll'],
	'/index':                         ['permitAll'],
	'/index.gsp':                     ['permitAll'],
	'/**/js/**':                      ['permitAll'],
	'/**/css/**':                     ['permitAll'],
	'/**/images/**':                  ['permitAll'],
	'/**/favicon.ico':                ['permitAll'],
	'/login/**':					  ['permitAll'],
	'/logout/**':          		      ['permitAll']
]


