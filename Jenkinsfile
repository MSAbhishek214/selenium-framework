pipeline {
    agent any

    environment {
        BROWSER = 'chrome'
        HEADLESS = 'true'
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/MSAbhishek214/selenium-framework.git', branch: 'main'
            }
        }

        stage('Build') {
            steps {
                echo 'Running build...'
                bat "mvn clean compile"
            }
        }

        stage('Test') {
            steps {
                echo "Executing tests in ${env.BROWSER} browser (headless=${env.HEADLESS})..."
                bat "mvn test -Dbrowser=${env.BROWSER} -Dheadless=${env.HEADLESS}"
            }
        }
    }

    post {
        success {
            echo 'Build and tests completed successfully ✅'
        }
        failure {
            echo 'Build or tests failed ❌ — Check the console for details'
        }
    }
}