
pipeline {
	agent any
	tools {
		maven "Maven"
	}
	parameters {
		[$class: 'DateParameterDefinition', name: 'somedate', dateFormat: 'yyyyMMdd', defaultValue: 'LocalDate.now()']
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
				echo "${params.somedate}"
    //                             sh 'docker login -u admin -p 123 192.168.10.135:8085'
				// sh 'docker build /var/lib/jenkins/workspace/simple-app/ -t 192.168.10.135:8085/petclinic-image:1.0'
				// sh 'docker push 192.168.10.135:8085/petclinic-image:1.0'
				echo 
			}
		}
	}
}
