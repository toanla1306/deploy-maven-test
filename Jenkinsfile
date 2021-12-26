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
                stage('unit test'){
                        steps{
                                sh 'mvn test > report.txt'
                                step( [ $class: 'JacocoPublisher' ] )
                        }
                }
        }
        post {
                always {
                        script {
                                check_status = sh(script: "cat report.txt | grep 'BUILD' | cut -d ' ' -f3", returnStdout: true).trim()
                                echo "test"
                                if("${check_status}" == 'SUCCESS'){
                                        sh "echo Subject: ${check_status} - simple app - ${env.BUILD_NUMBER} > message.txt"
                                        sh "echo -e 'BUILD SUCCESS\nlink build - http://192.168.10.141:8080/job/simple-app/${env.BUILD_NUMBER}/console >> message.txt"
                                }else{
                                        echo "esle"
                                        sh "echo Subject: ${check_status} - simple app - ${env.BUILD_NUMBER} > message.txt"
                                        sh "cat report.txt | grep 'ERROR' | sed 's/:/ /g'| sed '\$ a link build - http://192.168.10.141:8080/job/simple-app/${env.BUILD_NUMBER}/console' >> message.txt"
                                }
                                sh "ssmtp -V ldtoan1306@gmail.com < message.txt"  
                        }
                }
        }
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
//                 stage('check quality code') {
//                         steps {
//                                 script {
//                                         def scannerHome = tool 'sonarqube';
//                                         withSonarQubeEnv('sonarqube') {
//                                                 sh "${scannerHome}/bin/sonar-scanner \
//                                                 -D sonar.login=admin \
//                                                 -D sonar.password=123 \
//                                                 -D sonar.projectKey=sonarqubetest \
//                                                 -D sonar.test=src \
//                                                 -D sonar.test.inclusions=**/test/**,**/*.java \
//                                                 -D sonar.host.url=http://192.168.10.128:9000/"
//                                         }
//                                 }
//                         }
//                 }
//                 stage('unit test'){
//                         steps{
//                                 script{
//                                         try{ 
//                                                 //sh "cd ${env.WORKSPACE}/lib/; wget https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.8.2/junit-platform-console-standalone-1.8.2.jar"
//                                                 sh (script: "javac -d target -cp target:lib/junit-platform-console-standalone-1.8.2.jar src/test/java/org/springframework/samples/petclinic/**/*.java", returnStatus:true)
//                                         } catch(e) {
//                                                 echo e.toString()
//                                         } finally {
//                                                 sh "java -jar lib/junit-platform-console-standalone-1.8.2.jar --class-path target --select-package org.springframework.samples.petclinic.** --reports-dir='reports'"
//                                                 junit "/var/lib/jenkins/workspace/simple-app/reports/TEST-junit-jupiter.xml"
//                                         }
//                                 }
//                         }
//                 }
// 	}
}
