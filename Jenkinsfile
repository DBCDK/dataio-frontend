#!groovy

String docker_images_log_stash_tag = "docker_images_log"
String workerNode = "devel11-java11"
Boolean DEPLOY_TO_STAGING_CANDIDATE=false
//Byg!!
pipeline {
    agent {label workerNode}
    tools {
		jdk 'jdk11'
		maven 'Maven 3'
    }
    environment {
        MAVEN_OPTS="-Dmaven.repo.local=${env.WORKSPACE}/.m2/dataio-frontend-repo -XX:+TieredCompilation -XX:TieredStopAtLevel=1 -Dorg.slf4j.simpleLogger.showThreadName=true -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn"
        ARTIFACTORY_LOGIN = credentials("artifactory_login")
        GITLAB_PRIVATE_TOKEN = credentials("metascrum-gitlab-api-token")
        BUILD_NUMBER="${env.BUILD_NUMBER}"
        DOCKER_LABEL="${env.BRANCH_NAME == 'main' ? "${env.BUILD_NUMBER}" : "${env.BRANCH_NAME.toLowerCase()}-${env.BUILD_NUMBER}"}"
    }
    triggers {
        upstream(upstreamProjects: "Docker-payara5-bump-trigger",
			threshold: hudson.model.Result.SUCCESS)
    }
    options {
        skipDefaultCheckout(true)
        buildDiscarder(logRotator(artifactDaysToKeepStr: "",
            artifactNumToKeepStr: "", daysToKeepStr: "30", numToKeepStr: "30"))
        timestamps()
        timeout(time: 1, unit: "HOURS")
        disableConcurrentBuilds(abortPrevious: true)
    }
    stages {
        stage('clean and checkout') {
            steps {
                cleanWs()
                checkout scm
            }
        }
        stage("build") {
            steps {
                sh """#!/bin/bash
                    mvn -B -T 6 -Dtag="${DOCKER_LABEL}" install
                """
                script {
                    junit allowEmptyResults: true, testResults: '**/target/*-reports/*.xml'

                    def java = scanForIssues tool: [$class: 'Java']
                    publishIssues issues: [java], unstableTotalAll: 10

                    def pmd = scanForIssues tool: [$class: 'Pmd']
                    publishIssues issues: [pmd], unstableTotalAll: 1

                    def spotbugs = scanForIssues tool: [$class: 'SpotBugs']
                    publishIssues issues: [spotbugs], unstableTotalAll: 1
                }
            }
        }
        stage("push to artifactory ") {
            steps {
                sh """
                docker push docker-metascrum.artifacts.dbccloud.dk/dataio-gui:${DOCKER_LABEL}
            """
            }
        }
    }
}
