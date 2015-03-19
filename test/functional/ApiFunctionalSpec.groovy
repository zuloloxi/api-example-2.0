// grails test test-app functional: -inline

import org.codehaus.groovy.grails.commons.cfg.GrailsConfig;

import spock.lang.*
import groovy.util.AntBuilder
import groovy.json.*
import grails.util.Holders as grailsConfig

class ApiFunctionalSpec extends Specification {

	static Long createdPostId = null
	static Long createdPostVersion = null
	
	final String postId = '1'
	final String sectionId = '6'
	final String authorId = '1'
	
	def 'Validate User'() {
		when:
			def ant = new AntBuilder()
			
			// set this variable in your config or external properties file (preferable)
			String login = grailsConfig.config.root.login
			// set this variable in your config or external properties file (preferable)
			String password = grailsConfig.config.root.password
			ant.exec(outputProperty:"cmdOut",errorProperty:"cmdErr",resultProperty:"cmdExit",failonerror:"false",executable:"curl"){
				arg(line:"""--verbose --request POST --data "j_username=${login}&j_password=${password}&_spring_security_remember_me=checked" http://localhost:8080/api_v0.1/j_spring_security_check --cookie-jar cookies.txt""")
			}
			LinkedHashMap output = parseOutput(ant.project.properties.cmdErr)
		then:
			assert output.response.code.code == '302'
			assert output.response.code.message == 'Found'
	}

  def 'GET Request'() {
		when:
			def ant = new AntBuilder()
			ant.exec(outputProperty:"cmdOut",errorProperty:"cmdErr",resultProperty:"cmdExit",failonerror:"false",executable:"curl"){
				arg(line:"""--verbose --request GET --header "Content-Type: application/json" "http://localhost:8080/api_v0.1/post/show/${this.postId}" --cookie cookies.txt""")
			}
			LinkedHashMap errOutput = parseOutput(ant.project.properties.cmdErr)
			def json = new JsonSlurper().parseText(ant.project.properties.cmdOut)
			createdPostVersion = json.version
		then:
			assert errOutput.response.code.code == '200'
			assert json.collect(){it.key} == ['id', 'sectionId', 'statId', 'title','version']
  }
  
  def 'PUT Request'() {
	  Object json
	  LinkedHashMap errOutput = [:]
	  
	  when:
		  def ant = new AntBuilder()
		  ant.exec(outputProperty:"cmdOut",errorProperty:"cmdErr",resultProperty:"cmdExit",failonerror:"false",executable:"curl"){
			  arg(line:"""--verbose --header "Content-Type: application/json" -d "{'title': 'test post','teaser': 'This is just a test post to see if this works. Testing the api post system.','content':'Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vel consequat nisl, quis commodo neque. Integer ultrices vitae nulla lacinia rutrum. Duis ut porta arcu, sed gravida tortor. Donec pulvinar elit turpis, ultricies tristique mi auctor ac. Ut elementum ullamcorper risus ac sollicitudin. Morbi semper ultrices enim vel euismod. Proin eleifend orci ac elit mollis tempor. Nulla egestas odio eu volutpat eleifend. Nunc nec massa eget nisl sodales posuere. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nunc accumsan pretium sapien a tincidunt. Sed at fringilla mi.','section':${this.sectionId},'author':${this.authorId}}" --request PUT http://localhost:8080/api_v0.1/post/create --cookie cookies.txt""")
		  }
		  errOutput = parseOutput(ant.project.properties.cmdErr)
		  json = new JsonSlurper().parseText(ant.project.properties.cmdOut)
		  createdPostId = json.id
		  createdPostVersion = json.version
		then:
			assert errOutput.response.code.code == '200'
			assert json.collect(){it.key} == ['id','version']
  }

  def 'POST Request'() {
	  Object json
	  LinkedHashMap errOutput = [:]
	  
	  when:
		  def ant = new AntBuilder()
		  ant.exec(outputProperty:"cmdOut",errorProperty:"cmdErr",resultProperty:"cmdExit",failonerror:"false",executable:"curl"){
			  arg(line:"""--verbose --request POST --header "Content-Type: application/json" -d "{'title':'testamundo','version':${createdPostVersion}}" "http://localhost:8080/api_v0.1/post/update/${createdPostId}" --cookie cookies.txt""")
		  }
		  errOutput = parseOutput(ant.project.properties.cmdErr)
		  json = new JsonSlurper().parseText(ant.project.properties.cmdOut)
		  createdPostVersion = json.version
		then:
			assert errOutput.response.code.code == '200'
			assert json.collect(){it.key} == ['id','version']
  }

  def 'Post-Chain Request'() {
	  Object json
	  LinkedHashMap errOutput = [:]
	  
	  when:
		  def ant = new AntBuilder()
		  ant.exec(outputProperty:"cmdOut",errorProperty:"cmdErr",resultProperty:"cmdExit",failonerror:"false",executable:"curl"){
			  arg(line:"""--verbose --request POST --header "Content-Type: application/json" -d "{'sectionName':'YabbaDabbaDoo','chain':{'key':'sectionId','combine':'false','type':'postchain','order':{'section/show':'id','section/update':'return'}}}" "http://localhost:8080/api_v0.1/post/show/${createdPostId}" --cookie cookies.txt""")
		  }
		  errOutput = parseOutput(ant.project.properties.cmdErr)
		  json = new JsonSlurper().parseText(ant.project.properties.cmdOut)
	then:
		assert errOutput.response.code.code == '200'
		assert json.collect(){it.key} == ['id','sectionName','version']
  }

  def 'DELETE Request'() {
	  Object json
	  LinkedHashMap errOutput = [:]
	  
	  when:
		  def ant = new AntBuilder()
		  ant.exec(outputProperty:"cmdOut",errorProperty:"cmdErr",resultProperty:"cmdExit",failonerror:"false",executable:"curl"){
			  arg(line:"""--verbose --request DELETE --header "Content-Type: application/json" "http://localhost:8080/api_v0.1/post/delete/${createdPostId}" --cookie cookies.txt""")
		  }
		  errOutput = parseOutput(ant.project.properties.cmdErr)
		  //json = new JsonSlurper().parseText(ant.project.properties.cmdOut)
		then:
			assert errOutput.response.code.code == '200'
  }

  LinkedHashMap parseOutput(String output){
	  //println("return code : "+ant.project.properties.cmdExit)
	  //println("stderr : "+ant.project.properties.cmdErr)
	  //println("stdout : "+ant.project.properties.cmdOut)
	  def req = [:]
	  def resp = [:]
	  output.splitEachLine("//"){ it ->
		  //println(it)
		  it.each(){ it2 ->
			  if(it2 =~ />.+/){
				  if(it2.size()>3){
					  it2 = it2[2..-1]
					  if(it2.contains(":")){
						  def temp = it2.split(":")
						  req[temp[0]] = (temp[1])?temp[1]:[]
					  }else{
						  def temp = it2.split(" ")
						  req['uri'] = ['method':temp[0],'uri':temp[1],'protocol':temp[2]]
					  }
				  }
			  }

			  if(it2 =~ /<.+/){
				  if(it2.size()>3){
					  it2 = it2[2..-1]
					  if(it2.contains(":")){
						  def temp = it2.split(":")
						  resp[temp[0]] = (temp[1])?temp[1]:[]
					  }else{
						  def temp = it2.split(" ")
						  resp['code'] = ['protocol':temp[0],'code':temp[1],'message':temp[2]]
					  }
				  }
			  }
		  }
	  }
	  
	  return ['request':req,'response':resp]
  }

}