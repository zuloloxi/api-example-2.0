package net.nosegrind

import grails.plugin.springsecurity.annotation.Secured

@Secured('permitAll')
class ErrorController {

    def index(Integer max) {
        redirect(action:'error')
    }

	def error() {
		
		println "################################################"
		println request.exception
		println request.exception.cause
		println "################################################"
		return
	}
}
