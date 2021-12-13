import java.time.*
import java.time.format.DateTimeFormatter

def now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
def groovy_file

pipeline {
	agent none
	tools {
		maven "Maven"
	}
	stages {
                stage('Load file groovy') {
                      agent any
                      steps {
                              script {
                                      groovy_file = load "script.groovy"
                              }
                      }
                }
// 		stage('Deploy to Nexus'){
//                         agent any
// 			steps{
//                 sh "mvn -X clean deploy"
//                 sh 'curl -L -X GET "http://192.168.10.135:8081/service/rest/v1/search/assets/download?sort=version&repository=simpleapp-snapshot&maven.groupId=org.springframework.samples&maven.artifactId=spring-petclinic&maven.extension=jar" -H "accept: application/json" --output /var/lib/jenkins/workspace/simple-app/petclinic.jar'		
// 			}
// 		}
// 		stage('build docker image'){
// 			agent any
// 			steps{
//                                 script {
//                                         def VERSION_APP= sh(script: 'head -20 /var/lib/jenkins/workspace/simple-app/pom.xml | grep "<version>" | tail -1 | cut -d ">" -f2 | cut -d "<" -f1',returnStdout: true).trim()
//                                         def BUILD_ID_IN_DAY= sh(script: "curl http://192.168.10.135:8081/service/rest/repository/browse/simpleapp-snapshot/org/springframework/samples/spring-petclinic/2.5.0-SNAPSHOT/ | grep 2.5.0-${now} | wc -l",returnStdout: true).trim()
//                                         echo "${now}-${VERSION_APP}-${BUILD_ID_IN_DAY.toInteger() + 1}"
//                                         withCredentials([usernamePassword(credentialsId:'dockerlogin', passwordVariable: 'password', usernameVariable: 'username')]) {
//                                                 sh "docker login -u $username -p $password 192.168.10.135:8085"
//                                         }
//                                         sh 'docker login -u admin -p 123 192.168.10.135:8085'
// 				        sh "docker build /var/lib/jenkins/workspace/simple-app/ -t 192.168.10.135:8085/petclinic-image:${now}-${VERSION_APP}-${BUILD_ID_IN_DAY.toInteger() + 1}"
// 				        sh "docker push 192.168.10.135:8085/petclinic-image:${now}-${VERSION_APP}-${BUILD_ID_IN_DAY.toInteger() + 1}"
//                                 }        
// 			}
// 		}
		stage('check health release'){
			agent {
				label 'releasevm'
			}
			steps{
                                script{
//                                         def VERSION_APP= sh(script: 'head -20 /home/toan/agent_release/workspace/simple-app/pom.xml | grep "<version>" | tail -1 | cut -d ">" -f2 | cut -d "<" -f1',returnStdout: true).trim()
//                                         def BUILD_ID_IN_DAY= sh(script: "curl http://192.168.10.135:8081/service/rest/repository/browse/simpleapp-snapshot/org/springframework/samples/spring-petclinic/2.5.0-SNAPSHOT/ | grep 2.5.0-${now} | wc -l",returnStdout: true).trim()
//                                         withCredentials([usernamePassword(credentialsId:'dockerlogin', passwordVariable: 'password', usernameVariable: 'username')]) {
//                                                 sh "docker login -u $username -p $password 192.168.10.135:8085"
//                                         }
//                                         sh "docker pull 192.168.10.135:8085/petclinic-image:${now}-${VERSION_APP}-${BUILD_ID_IN_DAY.toInteger() + 1}"
//                                         sh "docker run --name check-health-${now}-${VERSION_APP}-${BUILD_ID_IN_DAY.toInteger() + 1} -d -p 8085:8080 192.168.10.135:8085/petclinic-image:${now}-${VERSION_APP}-${BUILD_ID_IN_DAY.toInteger() + 1}"
                                        // def status_health_check= sh(script: 'curl -I 192.168.10.140:8085 | grep HTTP | cut -d " " -f2', returnStdout: true).trim()
                                        // if("${status_health_check}" == "200"){
                                        //         echo "Deploy Sucess"
                                        // }else{
                                        //         error "Deploy Failed"
                                        // }
                                        groovy_file.loginDockerwithNexus()
                                        test = groovy_file.getVersionApp()
                                        echo "${test}"
                                }
			}
		}
	}
}
