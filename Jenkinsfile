import java.time.*
import java.time.format.DateTimeFormatter

def now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

pipeline {
	agent any
	tools {
		maven "Maven"
	}
        parameters {
                string(name: 'TIME', defaultValue: now, description:'')
        }
	stages {
// 		stage('Deploy to Nexus'){
// 			steps{
//                 sh "mvn -X clean deploy"
//                 sh 'curl -L -X GET "http://192.168.10.135:8081/service/rest/v1/search/assets/download?sort=version&repository=simpleapp-snapshot&maven.groupId=org.springframework.samples&maven.artifactId=spring-petclinic&maven.extension=jar" -H "accept: application/json" --output /var/lib/jenkins/workspace/simple-app/petclinic.jar'		
// 			}
// 		}
		stage('build docker image'){
			steps{
                                echo "${params.TIME}"
                                sh "export tag_image=${params.TIME}-${env.BUILD_ID}"
                                echo "$(tag_image)"
                                echo "build id: ${env.BUILD_ID}, build number: ${env.BUILD_NUMBER}"
    //                             sh 'docker login -u admin -p 123 192.168.10.135:8085'
				// sh 'docker build /var/lib/jenkins/workspace/simple-app/ -t 192.168.10.135:8085/petclinic-image:1.0'
				// sh 'docker push 192.168.10.135:8085/petclinic-image:1.0'
			}
		}
	}
}
