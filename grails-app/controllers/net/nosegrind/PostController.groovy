package net.nosegrind

import java.util.Date;

import javax.servlet.http.HttpServletResponse
import grails.plugin.springsecurity.annotation.Secured
import net.nosegrind.apitoolkit.*

@Secured(["isAuthenticated()"])
class PostController {

	def springSecurityService
	def apiLayerService
                             
	def defaultAction = 'list'
	
	def list(){
		params.max = (params.max) ? params.max?.toInteger() : 10
		params.offset = (params.offset) ? params.offset?.toInteger() : 0
		
		def post =  Post.withCriteria() {
			maxResults(params.max?.toInteger())
			firstResult(params.offset?.toInteger())
			and {order('creationDate','desc')}
		}
		
		def rowCount = Post.createCriteria().get() {
			projections {
				rowCount()
			}
		}
		LinkedHashMap model = [:]
		respond model as Object, [model: [posts:post, total:rowCount]]
	}
	
	def listtByTopic(){
		def topic = Topic.get(params.id);
		
		params.max = (params.max) ? params.max : 10
		params.offset = (params.offset) ? params.offset : 0

		def post =  Post.withCriteria() {
			maxResults(params.max?.toInteger())
			firstResult(params.offset?.toInteger())
			and {
				order('modifiedDate','desc')
				topics{eq("topic", topic)}
			}
		}
		
		def rowCount = Post.createCriteria().get() {
			topics{eq("topic", topic)}
			projections {rowCount()}
		}
		LinkedHashMap model = [:]
		respond model as Object, [model:[topic:topic, posts:post, params:params, total:rowCount]]
	}

	def listBySection(){
		println(params)
		def section = Section.get(params.id.toLong());
		
		def post =  Post.withCriteria() {
			order('modifiedDate','desc')
			eq("section", section)
		}
		
		def rowCount = Post.createCriteria().get() {
			eq("section", section)
			projections {rowCount()}
		}
		LinkedHashMap model = [:]
		respond model as Object,  [model:[section:section, posts:post, params:params, total:rowCount]]
	}
	
	def show(){
		respond Post.get(params.id.toLong())
		return null
	}
	
	def save(){
		Person person = springSecurityService.currentUser
		Post post = new Post();

		post.title = params.title
		post.teaser = params.teaser
		post.content = params.content
		post.section = Section.get(params.section.toLong())
		post.stat = Status.get(5)
		
		Date now = new Date();
		post.creationDate = now;
		post.modifiedDate = now;
		post.endCommentsDate = (params.endCommentsDate) ? params.endCommentsDate : null
		
		post.author = person.id

		if (post.hasErrors()) {
			render(status:HttpServletResponse.SC_NOT_FOUND, text: 'Could not save Post. Check your data and try again')
		}
		
		if (!post.save(flush:true)) {
			render(status:HttpServletResponse.SC_NOT_FOUND, text: 'Could not save Post. Check your data and try again')
		}else{
			respond Post.get(post.id.toLong())
		}
		return null
	}
	
	def update(Post postInstance){
		Person person = springSecurityService.currentUser
		List roles = springSecurityService.getPrincipal().getAuthorities()
		if(postInstance.author==person.id || roles.contains('ROLE_ADMIN')){
			long version = postInstance.version
			
			postInstance.title = params.title
			postInstance.teaser = params.teaser
			postInstance.content = params.content
			postInstance.section = Section.get(params.section.toLong())
			postInstance.stat = Status.get(params.statId.toLong())
			
			Date now = new Date();
			postInstance.modifiedDate = now;
			
			if (postInstance == null){
				render(status:HttpServletResponse.SC_NOT_FOUND)
			}
	
			if (postInstance.hasErrors()) {
				render(status:HttpServletResponse.SC_NOT_FOUND)
			}
	
			if (version!=params.version.toLong()) {
				render(status:HttpServletResponse.SC_BAD_REQUEST, text: 'Another user has updated this Post while you were editing.')
			}
			
			postInstance.save flush:true
			//apiToolkitService.callHook('test',testInstance,'update')

			respond Post.get(postInstance.id.toLong())
		}else{
			render(status:HttpServletResponse.SC_NOT_FOUND)
		}
		return null
	}
	
	def delete(Post postInstance){
		if (postInstance == null) {
			render(status:HttpServletResponse.SC_NOT_FOUND)
		}

		postInstance.delete flush:true
		return null
	}
}
