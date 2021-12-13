def getVersionApp() {
        sh(script: "head -20 ${env.WORKSPACE}/simple-app/pom.xml | grep '<version>' | tail -1 | cut -d '>' -f2 | cut -d '<' -f1",returnStdout: true).trim()
}

return this
