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
//                                         tag_image_docker = groovy_file.getTagsImageDocker()
//                                         groovy_file.loginDockerwithNexus()
//                                         sh 'docker login -u admin -p 123 192.168.10.135:8085'
// 				        sh "docker build /var/lib/jenkins/workspace/simple-app/ -t 192.168.10.135:8085/petclinic-image:${tag_image_docker}"
// 				        sh "docker push 192.168.10.135:8085/petclinic-image:${tag_image_docker}"
//                                 }        
// 			}
// 		}
		stage('check health release'){
			agent {
				label 'releasevm'
			}
			steps{
                                script{
                                        tag_image_docker = groovy_file.getTagsImageDocker()
                                        groovy_file.loginDockerwithNexus()
                                        sh "docker pull 192.168.10.135:8085/petclinic-image:${tag_image_docker}"
                                        check_list_container_null = sh(script: "docker ps -a | wc -l", returnStdout: true).trim()
                                        if("${check_list_container_null}" != "1") {
                                                        id_container_old = sh(script: "docker ps -a | tail -1 | cut -d ' ' -f1", returnStdout: true).trim()
                                                        sh "docker stop ${id_container_old}"
                                                }
                                        sh "docker run --name check-health-${id_container_old} -d -p 8085:8080 192.168.10.135:8085/petclinic-image:${tag_image_docker}"
                                        sleep(time:10,unit:"SECONDS")
                                        status_health_check= groovy_file.checkHealthDeploy()
                                        if("${status_health_check}" == "203"){
                                                if("${check_list_container_null}" != "1") {
                                                        sh "docker rm ${id_container_old}"
                                                }
                                                echo "Deploy Sucess"
                                        }else{
                                                id_container_new = sh(script: "docker ps -a | grep check-health-${tag_image_docker} | cut -d ' ' -f1", returnStdout: true).trim()
                                                sh "docker stop ${id_container_new}"
                                                sh "docker rm ${id_container_new}"
                                                sh "docker start ${id_container_old}"
                                                
                                                echo "Deploy Failed"
                                        }
                                }
			}
		}
	}
}
