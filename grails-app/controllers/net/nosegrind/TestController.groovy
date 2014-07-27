package net.nosegrind

import grails.plugin.springsecurity.annotation.Secured
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import net.nosegrind.apitoolkit.*
import net.nosegrind.apitoolkit.Api
import net.nosegrind.apitoolkit.ApiStatuses

@Secured(["isAuthenticated()"])
class TestController {

    //static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

	def apiLayerService
	ApiStatuses error = new ApiStatuses()
	
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Test.list(params), model:[testInstanceCount: Test.count()]
    }

	//@Secured(['ROLE_ADMIN','ROLE_USER'])
	//@Api(method="GET",name='Test',description="Get fred data",roles=['ROLE_ADMIN','ROLE_USER'])
	def fred() {
		respond Test.get(params.id)
		return null
	}

	
	//@Secured(['ROLE_ADMIN','ROLE_USER'])
	//@Api(method="GET",name='Test',description="Get test data",roles=['ROLE_ADMIN','ROLE_USER'])
    def show() {
		respond Test.get(params.id)
		return null
    }

/*
    def create() {
        respond new Test(params)
    }
    */

    @Transactional
	@Secured(['ROLE_ADMIN','ROLE_USER'])
	//@Api(method="PUT",name='Test',description="Save test data",roles=['ROLE_ADMIN','ROLE_USER'])
    def save() {
		Test testInstance = new Test()
		testInstance.testdata = params.testdata
		
		if(apiLayerService.isApiCall()){
		        if (testInstance == null) {
					return
		        }
		
		        if (testInstance.hasErrors()) {
		            respond testInstance.errors, view:'create'
					return
		        }
		        
			if (testInstance.save(flush:true)) {
				respond testInstance
			}
		}
		return
    }

    def edit(Test testInstance) {
        respond testInstance
    }

    @Transactional
	//@Secured(['ROLE_ADMIN','ROLE_USER'])
	//@Api(method="POST",name='Test',description="Update test data",roles=['ROLE_ADMIN','ROLE_USER'])
    def update(Test testInstance) {
		println("update called")
			testInstance.testdata = params.testdata

	        if (testInstance == null){
				error._404_NOT_FOUND().send()
				return null
	        }
	
	        if (testInstance.hasErrors()) {
				error._404_NOT_FOUND().send()
				return null
	        }
	
	        testInstance.save flush:true
			//apiToolkitService.callHook('test',testInstance,'update')
			respond Test.get(testInstance.id)
			return null

    }

    @Transactional
	//@Secured(['ROLE_ADMIN','ROLE_USER'])
    def delete() {
		Test testInstance = new Test(params)
        if (testInstance == null) {
            notFound()
            return
        }

        testInstance.delete flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Test.label', default: 'Test'), testInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'testInstance.label', default: 'Test'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
	
	@Secured('permitAll')
	def test(){
		println(params)
	}
}
