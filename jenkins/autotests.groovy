timeout(60){
    node("maven"){
        currentBuild.description= "BRANCH=${BRANCH}\nBASE_URL=${BASE_URI}"
        stage("Checkout") {
            checkout scm
        }
        stage("Running tests"){
            def exitCode = sh (
                returnStatus:true,
                script:" mvn test -DBase.uri=${BASE_URI}"
            )
            if(exitCode==1){
                currentBuild.result = 'UNSTABLE'
            }
        }
        stage("Allure report"){
            allure([
                    invludeProperties:false,
                    jdk:'',
                    properties:[],
                    reportBuildPolicy:'ALWAYS',
                    results:[[path:'allure-results']]
            ])
        }
    }
}