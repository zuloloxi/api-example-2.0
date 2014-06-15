package net.nosegrind

import grails.plugin.springsecurity.annotation.Secured

@Secured('permitAll')
class ErrorController {

    def index(Integer max) {
        redirect(action:'error')
    }

	def error() {
		respond request.exception
		return
	}
}
