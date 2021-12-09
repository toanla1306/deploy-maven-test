import java.time.*
import java.time.format.DateTimeFormatter

def now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

pipeline {
	agent any
	tools {
		maven "Maven"
	}
        environment {
                VERSION_APP= sh(script: 'unzip -p /var/lib/jenkins/workspace/simple-app/petclinic.jar | head | grep Implementation-Version | cut -d ":" -f2)', ,returnStdout: true).trim()
	stages {
// 		stage('Deploy to Nexus'){
// 			steps{
//                 sh "mvn -X clean deploy"
//                 sh 'curl -L -X GET "http://192.168.10.135:8081/service/rest/v1/search/assets/download?sort=version&repository=simpleapp-snapshot&maven.groupId=org.springframework.samples&maven.artifactId=spring-petclinic&maven.extension=jar" -H "accept: application/json" --output /var/lib/jenkins/workspace/simple-app/petclinic.jar'		
// 			}
// 		}
		stage('build docker image'){
			steps{
                                echo "${now}.${env.BUILD_ID}"
                                echo "build id: ${env.BUILD_ID}, build number: ${env.BUILD_NUMBER}"
//                              sh 'export -n test=$(unzip -p /var/lib/jenkins/workspace/simple-app/petclinic.jar | head | grep Implementation-Version | cut -d ":" -f2)'
                                echo "${params.VERSION_APP}"
//                                 sh 'docker login -u admin -p 123 192.168.10.135:8085'
// 				sh "docker build /var/lib/jenkins/workspace/simple-app/ -t 192.168.10.135:8085/petclinic-image:${now}.${env.BUILD_ID}"
// 				sh "docker push 192.168.10.135:8085/petclinic-image:${now}.${env.BUILD_ID}"
			}
		}
	}
}
