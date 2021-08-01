pipeline {
    agent any
    tools {
        maven 'maven'
    }

    triggers {
        githubPush()
    }

    parameters {
        string(name: 'GIT_URL', defaultValue: 'https://github.com/shomrina/klippert_javaQA.git', description: 'The target git url')
        string(name: 'GIT_BRANCH', defaultValue: 'seleniumAllureJenkins', description: 'The target git branch')
        choice(name: 'BROWSER', choices: ['chrome', 'firefox'], description: 'Pick the target browser in Selenium')
        choice(name: 'OPTIONS', choices:['start-maximized', 'headless'], description: 'The browser options')
        string(name: 'LOGIN', description: 'Personal Login in LK Otus')
        string(name: 'PASSWORD', description: 'Personal Password in LK Otus')
    }

    stages {
        stage('Pull from GitHub') {
            steps {
                slackSend(message: "Notification from Jenkins Pipeline")
                git ([
                    url: "${params.GIT_URL}",
                    branch: "${params.GIT_BRANCH}"
                    ])
            }
        }
        stage('Run maven clean test') {
            steps {
                sh 'mvn clean test -Dbrowser=$BROWSER_NAME -Doptions=$OPTIONS -Dlogin=$LOGIN -Dpassword=$PASSWORD'
            }
            post {

                always {
                    archiveArtifacts artifacts: '**/target/', fingerprint: true

                  script {
                    if (currentBuild.currentResult == 'SUCCESS') {
                    step([$class: 'Mailer', notifyEveryUnstableBuild: true, recipients: "test.marina.qa.otus@gmail.com", sendToIndividuals: true])
                    } else {
                    step([$class: 'Mailer', notifyEveryUnstableBuild: true, recipients: "test.marina.qa.otus@gmail.com", sendToIndividuals: true])
                    }

                    ws("$workspace/"){

                    // Формирование отчета
                    allure([
                      includeProperties: false,
                      jdk: '',
                      properties: [],
                      reportBuildPolicy: 'ALWAYS',
                      results: [[path: 'target/allure-results']]
                    ])
                    }

                    println('allure report created')

                    // Узнаем ветку репозитория
                    def branch = sh(returnStdout: true, script: 'git rev-parse --abbrev-ref HEAD\n').trim().tokenize().last()
                    println("branch= " + branch)

                    // Достаем информацию по тестам из junit репорта
                    def summary = junit testResults: '**/target/surefire-reports/*.xml'
                    println("summary generated")

                    // Текст оповещения
                    def message = "${currentBuild.currentResult}: Job ${env.JOB_NAME}, build ${env.BUILD_NUMBER}, branch ${branch}\nTest Summary - ${summary.totalCount}, Failures: ${summary.failCount}, Skipped: ${summary.skipCount}, Passed: ${summary.passCount}\nMore info at: ${env.BUILD_URL}"
                    println("message= " + message)
                  }
                }
            }
        }
    }
}