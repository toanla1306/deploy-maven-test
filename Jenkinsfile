
pipeline {
	agent any
	tools {
		maven "Maven"
	}
	stages {
		stage('Upload War log Nexus'){
			steps{
                             sh "mvn -X clean deploy"			
			}
		}
	}
	post{
		success{
            sh 'curl -L -X GET "http://192.168.10.139:8081/service/rest/v1/search/assets/download?sort=version&repository=simpleapp-snapshot&maven.groupId=org.springframework.samples&maven.artifactId=spring-petclinic&maven.extension=jar" -H "accept: application/json" --output /var/lib/jenkins/Downloads/petclinic.jar'
            sh "curl --insecure --user toan:123 -T /var/lib/jenkins/Downloads/petclinic.jar sftp://192.168.10.140/home/toan/Downloads/"
		}
	}
}
