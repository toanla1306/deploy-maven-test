import java.time.*
import java.time.format.DateTimeFormatter

def now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))

pipeline {
	agent none
	tools {
		maven "Maven"
	}
	stages {
		agent any
		stage('Deploy to Nexus'){
			steps{
                sh "mvn -X clean deploy"
                sh 'curl -L -X GET "http://192.168.10.135:8081/service/rest/v1/search/assets/download?sort=version&repository=simpleapp-snapshot&maven.groupId=org.springframework.samples&maven.artifactId=spring-petclinic&maven.extension=jar" -H "accept: application/json" --output /var/lib/jenkins/workspace/simple-app/petclinic.jar'		
			}
		}
		stage('build docker image'){
			agent any
			steps{
                                script {
                                        def VERSION_APP= sh(script: 'head -20 /var/lib/jenkins/workspace/simple-app/pom.xml | grep "<version>" | tail -1 | cut -d ">" -f2 | cut -d "<" -f1',returnStdout: true).trim()
                                        def BUILD_ID_IN_DAY= sh(script: "curl http://192.168.10.135:8081/service/rest/repository/browse/simpleapp-snapshot/org/springframework/samples/spring-petclinic/2.5.0-SNAPSHOT/ | grep 2.5.0-${now} | wc -l",returnStdout: true).trim()
                                        echo "${now}-${VERSION_APP}-${BUILD_ID_IN_DAY.toInteger() + 1}"
                                        sh 'docker login -u admin -p 123 192.168.10.135:8085'
				        sh "docker build /var/lib/jenkins/workspace/simple-app/ -t 192.168.10.135:8085/petclinic-image:${now}-${VERSION_APP}-${BUILD_ID_IN_DAY.toInteger() + 1}"
				        sh "docker push 192.168.10.135:8085/petclinic-image:${now}-${VERSION_APP}-${BUILD_ID_IN_DAY.toInteger() + 1}"
                                }        
			}
		}
		stage('check health release'){
			agent {
				label 'releasevm'
			}
			steps{
				echo "hello release"
			}
		}
	}
}
