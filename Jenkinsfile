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
		stage('Deploy to Nexus'){
                        agent any
			steps{
                sh "mvn -X clean deploy"
                sh 'curl -L -X GET "http://nexus-repository.com:8081/service/rest/v1/search/assets/download?sort=version&repository=simpleapp-snapshot&maven.groupId=org.springframework.samples&maven.artifactId=spring-petclinic&maven.extension=jar" -H "accept: application/json" --output /var/lib/jenkins/workspace/simple-app/petclinic.jar'		
			}
		}
		stage('build docker image'){
			agent any
			steps{
                                script {
                                        tag_image_docker = groovy_file.getTagsImageDocker()
                                        groovy_file.loginDockerwithNexus()
				        sh "docker build ${env.WORKSPACE} -t nexus-repository.com:8085/petclinic-image:${tag_image_docker}"
				        sh "docker push nexus-repository.com:8085/petclinic-image:${tag_image_docker}"
                                }        
			}
		}
		stage('check health release'){
			agent {
				label 'releasevm'
			}
			steps{
                                script{
                                        tag_image_docker = groovy_file.getTagsImageDocker()
                                        groovy_file.loginDockerwithNexus()
                                        sh "docker pull nexus-repository.com:8085/petclinic-image:${tag_image_docker}"
                                        check_list_container_null = sh(script: "docker ps -a | wc -l", returnStdout: true).trim()
                                        if("${check_list_container_null}" != "1") {
                                                        id_container_old = sh(script: "docker ps -a | tail -1 | cut -d ' ' -f1", returnStdout: true).trim()
                                                        sh "docker stop ${id_container_old}"
                                                }
                                        sh "docker run --name check-health-${id_container_old} -d -p 8085:8080 nexus-repository.com:8085/petclinic-image:${tag_image_docker}"
                                        sleep(time:10,unit:"SECONDS")
                                        status_health_check= groovy_file.checkHealthDeploy()
                                        if("${status_health_check}" == "202"){
                                                if("${check_list_container_null}" != "1") {
                                                        sh "docker rm ${id_container_old}"
                                                }
                                                echo "Deploy Sucess"
                                        }else{
                                                id_container_new = sh(script: "docker ps -a | head -2 | tail -1 | cut -d ' ' -f1", returnStdout: true).trim()
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
