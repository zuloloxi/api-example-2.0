import net.nosegrind.apitoolkit.Role
import net.nosegrind.apitoolkit.Person
import net.nosegrind.apitoolkit.PersonRole

import net.nosegrind.apitoolkit.*


class BootStrap {

	def grailsApplication
	
    def init = { servletContext ->
		


		environments {
			//production {}
			production {
				Role rootRole = Role.findByAuthority('ROLE_ADMIN')?: new Role(authority:'ROLE_ADMIN').save(faileOnError:true)
				//Role userRole = Role.findByAuthority('ROLE_USER')?: new Role(authority:'ROLE_USER').save(faileOnError:true)
		
				// DEFAULT ADMIN
				Person user = Person.findByUsername("${grailsApplication.config.root.login}")
				PersonRole.withTransaction(){ status ->
					Role adminRole = Role.findByAuthority("ROLE_ADMIN")
					if(!user?.id){
						user = new Person(username:"${grailsApplication.config.root.login}",password:"${grailsApplication.config.root.password}",email:"${grailsApplication.config.root.email}")
						user.save(flush:true,failOnError:true)
					}else{
						user.password = "${grailsApplication.config.root.password}"
						user.save(flush:true,failOnError:true)
					}
				
					if(!user?.authorities?.contains(adminRole)){
						PersonRole.create user,adminRole
					}
					
					status.isCompleted()
				}
			}
			test{}

		}

    }
    def destroy = {
    }
}
