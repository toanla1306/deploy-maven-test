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
//                 sh 'curl -L -X GET "http://nexus-repository.com:8081/service/rest/v1/search/assets/download?sort=version&repository=simpleapp-snapshot&maven.groupId=org.springframework.samples&maven.artifactId=spring-petclinic&maven.extension=jar" -H "accept: application/json" --output /var/lib/jenkins/workspace/simple-app/petclinic.jar'		
// 			}
// 		}
// 		stage('build docker image'){
// 			agent any
// 			steps{
//                                 script {
//                                         tag_image_docker = groovy_file.getTagsImageDocker()
//                                         groovy_file.loginDockerwithNexus()
// 				        sh "docker build ${env.WORKSPACE} -t nexus-repository.com:8085/petclinic-image:${tag_image_docker}"
// 				        sh "docker push nexus-repository.com:8085/petclinic-image:${tag_image_docker}"
//                                 }        
// 			}
// 		}
// 		stage('check health release'){
// 			agent {
// 				label 'releasevm'
// 			}
// 			steps{
//                                 script{
//                                         tag_image_docker = groovy_file.getTagsImageDocker()
//                                         groovy_file.loginDockerwithNexus()
//                                         groovy_file.checkHealthDeploy()
//                                 }
// 			}
// 		}
                stage('test sshpass') {
                        agent any
                        steps {
                                script{
                                        groovy_file.sshReleaseVM("ls -la | grep Downloads")
                                }
                        }
                }
	}
}
