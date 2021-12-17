def groovy_file

pipeline {
	agent any
	tools {
		maven "Maven"
	}
	stages {
//                 stage('Load file groovy') {
//                       steps {
//                               script {
//                                       groovy_file = load "script.groovy"
//                               }
//                       }
//                 }
// 		stage('Deploy to Nexus'){
// 			steps{
//                                 sh "mvn -X clean deploy"
//                                 sh 'curl -L -X GET "http://nexus-repository.com:8081/service/rest/v1/search/assets/download?sort=version&repository=simpleapp-snapshot&maven.groupId=org.springframework.samples&maven.artifactId=spring-petclinic&maven.extension=jar" -H "accept: application/json" --output /var/lib/jenkins/workspace/simple-app/petclinic.jar'		
// 			}
// 		}
// 		stage('build docker image'){
// 			steps{
//                                 script {
//                                         tag_image_docker = groovy_file.getTagsImageDocker()
//                                         groovy_file.loginDockerwithNexus('JenkinsVM')
// 				        sh "docker build ${env.WORKSPACE} -t nexus-repository.com:8085/petclinic-image:${tag_image_docker}"
// 				        sh "docker push nexus-repository.com:8085/petclinic-image:${tag_image_docker}"
//                                 }        
// 			}
// 		}
// 		stage('check health release'){
// 			steps{
//                                 script{
//                                         tag_image_docker = groovy_file.getTagsImageDocker()
//                                         groovy_file.loginDockerwithNexus('ReleaseVM')
//                                         groovy_file.checkHealthDeploy()
//                                 }
// 			}
// 		}
                stage('unit test') {
                        def scannerHome = tool 'sonarqube';
                        withSonarQubeEnv('sonarqube') {
                                sh "${scannerHome}/bin/sonar-scanner \
                                -D sonar.login=admin \
                                -D sonar.password=123 \
                                -D sonar.projectKey=sonarqubetest \
                                -D sonar.exclusions=vendor/**,resources/**, **/*.java\
                                -D sonar.host.url=http://192.168.10.136:9000/"
                        }
                }
	}
}
