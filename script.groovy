import java.time.*
import java.time.format.DateTimeFormatter

def getTime(){
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
}
def getVersionApp() {
        sh(script: "head -10 ${env.WORKSPACE}/pom.xml | grep '<version>' | tail -1 | cut -d '>' -f2 | cut -d '<' -f1 | cut -d '-' -f1",returnStdout: true).trim()
}

def getVersionBuildinDay() {
        now = getTime()
        version_app = getVersionApp()
        sh(script: "curl http://nexus-repository.com:8081/service/rest/repository/browse/simpleapp-snapshot/org/springframework/samples/spring-petclinic/2.5.0-SNAPSHOT/ | grep ${version_app}-${now} | wc -l",returnStdout: true).trim()
}

def getTagsImageDocker() {
        now = getTime()
        version_app = getVersionApp()
        version_build_in_day = getVersionBuildinDay()
        return "${now}-${version_app}-${version_build_in_day}"
}

def loginDockerwithNexus() {
        withCredentials([usernamePassword(credentialsId:'dockerlogin', passwordVariable: 'password', usernameVariable: 'username')]) {
                                                sh "docker login -u $username -p $password nexus-repository.com:8085"
                                        }
}

def checkHealthDeploy() {
        sh "docker pull nexus-repository.com:8085/petclinic-image:${tag_image_docker}"
        check_list_container_null = sh(script: "docker ps -a | wc -l", returnStdout: true).trim()
        if("${check_list_container_null}" != "1") {
                id_container_old = sh(script: "docker ps -a | tail -1 | cut -d ' ' -f1", returnStdout: true).trim()
                sh "docker stop ${id_container_old}"
        }
        sh "docker run --name check-health-${id_container_old} -d -p 8085:8080 nexus-repository.com:8085/petclinic-image:${tag_image_docker}"
        sleep(time:10,unit:"SECONDS")
        status_health_check= sh(script: 'curl -I release-vm.com:8085 | grep HTTP | cut -d " " -f2', returnStdout: true).trim()
        if("${status_health_check}" == "200"){
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

def sshReleaseVM(commandline, value_return_stdout){
        withCredentials([usernamePassword(credentialsId:'vmrelease', passwordVariable: 'password', usernameVariable: 'username')]) {
                sh(script: "sshpass -p ${password} ssh -o stricthostkeychecking=no ${username}@release-vm.com ${commandline}", returnStdout: value_return_stdout)
        }
}

return this
