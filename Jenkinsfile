pipeline {
    agent any

    tools {
        maven 'Maven_3_9_10' // Match your Jenkins Maven tool name
    }

    environment {
        BROWSER = 'chrome'
        HEADLESS = 'true'
    }

    stages {
        stage('Build') {
            steps {
                echo '🔧 Running build...'
                bat '"%MAVEN_HOME%\\bin\\mvn" clean compile'
            }
        }

        stage('Test') {
            steps {
                echo "🧪 Executing tests in ${env.BROWSER} (headless=${env.HEADLESS})..."
                bat "\"%MAVEN_HOME%\\bin\\mvn\" test -Dbrowser=${env.BROWSER} -Dheadless=${env.HEADLESS}"
            }
        }

        stage('Publish Reports') {
            steps {
                echo '📄 Archiving Extent report...'
                archiveArtifacts artifacts: 'reports/ExtentReport_*.html', fingerprint: true
            }
        }
    }

    post {
        success {
            echo '✅ Build and tests completed successfully'
        }
        failure {
            echo '❌ Build or tests failed — check console and artifacts for clues'
        }
        always {
            echo '🧹 Cleaning workspace...'
            cleanWs()
        }
    }
}