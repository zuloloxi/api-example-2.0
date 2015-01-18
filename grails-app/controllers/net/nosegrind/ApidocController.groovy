//package net.nosegrind.apitoolkit
package net.nosegrind

import org.springframework.security.access.annotation.Secured
import org.codehaus.groovy.grails.commons.DefaultGrailsControllerClass

@Secured('permitAll')
class ApidocController {

	def apiCacheService
	def apiLayerService
	def springSecurityService
	
	def index(){
		redirect(action:'show')
	}
	
	@Secured('permitAll')
	def show(){
		Map docs = [:]
		grailsApplication.controllerClasses.each { DefaultGrailsControllerClass controllerClass ->
			String controllername = controllerClass.logicalPropertyName
			def cache = apiCacheService.getApiCache(controllername)
			List nonmethods = ['deprecated','defaultAction','domainPackage','value']
			if(cache){
				cache.each(){ it ->
					String apiversion = it.key
					it.value.each(){ it2 ->
						if(!nonmethods.contains(it2.key)){
							String actionname = it2.key
								if(!docs["$controllername"]){
									docs["${controllername}"] = [:]
								}
								if(!docs["$controllername"]["${actionname}"]){
									docs["${controllername}"]["${actionname}"] = [:]
								}
								docs[controllername][actionname][apiversion]=cache[apiversion][actionname]['doc']
						}
					}
				}

			}
		}
		
		String authority = springSecurityService.principal.authorities*.authority[0]
		authority = (authority=='ROLE_ANONYMOUS')?'permitAll':authority
		[apiList:docs,authority:authority]
	}
}

