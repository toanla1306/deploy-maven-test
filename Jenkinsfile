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
		stage('build docker image'){
			agent any
			steps{
                                script {
                                        VERSION_APP= groovy_file.getVersionApp()
                                        BUILD_ID_IN_DAY= groovy_file.getVersionBuildinDay()
                                        now = groovy_file.getTime()
                                        tag = groovy_file.getTagsImageDocker()
                                        echo tag
//                                         groovy_file.loginDockerwithNexus()
//                                         sh 'docker login -u admin -p 123 192.168.10.135:8085'
// 				        sh "docker build /var/lib/jenkins/workspace/simple-app/ -t 192.168.10.135:8085/petclinic-image:${now}-${VERSION_APP}-${BUILD_ID_IN_DAY.toInteger() + 1}"
// 				        sh "docker push 192.168.10.135:8085/petclinic-image:${now}-${VERSION_APP}-${BUILD_ID_IN_DAY.toInteger() + 1}"
                                }        
			}
		}
// 		stage('check health release'){
// 			agent {
// 				label 'releasevm'
// 			}
// 			steps{
//                                 script{
//                                         VERSION_APP= groovy_file.getVersionApp()
//                                         BUILD_ID_IN_DAY= groovy_file.getVersionBuildinDay()
//                                         now = groovy_file.getTime()
//                                         groovy_file.loginDockerwithNexus()
//                                         sh "docker pull 192.168.10.135:8085/petclinic-image:${now}-${VERSION_APP}-${BUILD_ID_IN_DAY.toInteger() + 1}"
//                                         sh "docker run --name check-health-${now}-${VERSION_APP}-${BUILD_ID_IN_DAY.toInteger() + 1} -d -p 8085:8080 192.168.10.135:8085/petclinic-image:${now}-${VERSION_APP}-${BUILD_ID_IN_DAY.toInteger() + 1}"
//                                         status_health_check= groovy_file.checkHealthDeploy()
//                                         if("${status_health_check}" == "200"){
//                                                 echo "Deploy Sucess"
//                                         }else{
//                                                 error "Deploy Failed"
//                                         }
//                                 }
// 			}
// 		}
	}
}
