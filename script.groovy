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
        echo now
        echo version_app
        sh(script: "curl http://nexus-repository.com:8081/service/rest/repository/browse/simpleapp-snapshot/org/springframework/samples/spring-petclinic/2.5.0-SNAPSHOT/ | grep 2.5.0-${now} | wc -l",returnStdout: true).trim()
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
        sh(script: 'curl -I release-vm.com:8085 | grep HTTP | cut -d " " -f2', returnStdout: true).trim()
}

return this
