
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
            sh 'curl -L -X GET "http://192.168.10.136:8081/service/rest/v1/search/assets/download?sort=version&repository=simpleapp-snapshot&maven.groupId=org.springframework.samples&maven.artifactId=spring-petclinic&maven.extension=jar" -H "accept: application/json" --output /var/jenkins_home/.m2/petclinic.war'
            sh "curl --insecure --user root:1234567 -T /var/jenkins_home/.m2/petclinic.war sftp://192.168.10.138/usr/share/tomcat/webapps/"
		}
	}
}
