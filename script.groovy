def getVersionApp() {
        sh(script: "head -20 ${env.WORKSPACE}/pom.xml | grep '<version>' | tail -1 | cut -d '>' -f2 | cut -d '<' -f1",returnStdout: true).trim()
}

def getVersionBuildinDay() {
        sh(script: "curl http://192.168.10.135:8081/service/rest/repository/browse/simpleapp-snapshot/org/springframework/samples/spring-petclinic/2.5.0-SNAPSHOT/ | grep 2.5.0-${now} | wc -l",returnStdout: true).trim()
}

def loginDockerwithNexus() {
        withCredentials([usernamePassword(credentialsId:'dockerlogin', passwordVariable: 'password', usernameVariable: 'username')]) {
                                                sh "docker login -u $username -p $password 192.168.10.135:8085"
                                        }
}

def checkHealthDeploy() {
        sh(script: 'curl -I 192.168.10.140:8085 | grep HTTP | cut -d " " -f2', returnStdout: true).trim()
}

return this